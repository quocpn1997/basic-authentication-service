package rey.be.authenticationservice.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rey.be.authenticationservice.domain.exception.EmailAlreadyExistsException;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.domain.model.UserInfoType;
import rey.be.authenticationservice.domain.model.UserVerification;
import rey.be.authenticationservice.domain.repository.UserRepository;
import rey.be.authenticationservice.domain.repository.UserVerificationRepo;
import rey.be.authenticationservice.domain.security.PasswordHasher;
import rey.be.authenticationservice.infra.mail.VerificationEmailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserVerificationRepo userVerificationRepo;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private VerificationEmailSender verificationEmailSender;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void register_User_normalizesHashesAndPersists_whenEmailIsNew() {
        var user = User.builder()
                .email("  New@Example.COM ")
                .fullName("  Jane Doe  ")
                .password("raw")
                .build();
        var persisted = User.builder().id(1L).email("new@example.com").fullName("Jane Doe").password("hashed").build();

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordHasher.hash("raw")).thenReturn("hashed");
        when(userRepository.save(user)).thenReturn(persisted);

        var result = registerService.registerUser(user);

        assertThat(result).isSameAs(persisted);
        assertThat(user.getEmail()).isEqualTo("new@example.com");
        assertThat(user.getFullName()).isEqualTo("Jane Doe");
        assertThat(user.getPassword()).isEqualTo("hashed");
        verify(userRepository).save(user);
    }

    @Test
    void register_User_throwsAndDoesNotPersist_whenEmailAlreadyExists() {
        var user = User.builder()
                .email("Dup@Example.com")
                .fullName("Dup")
                .password("raw")
                .build();

        when(userRepository.existsByEmail("dup@example.com")).thenReturn(true);

        assertThatThrownBy(() -> registerService.registerUser(user))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("Email already registered: dup@example.com");

        verify(userRepository, never()).save(any());
        verifyNoInteractions(passwordHasher);
    }

    @Test
    void verifyUser_persistsVerification_andDispatchesEmail_whenEmailIsNew() {
        var verification = UserVerification.builder()
                .userInfo("new@example.com")
                .userInfoType(UserInfoType.EMAIL)
                .verificationCode("123456")
                .build();

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userVerificationRepo.save(any(UserVerification.class))).thenReturn(verification);

        registerService.verifyUser("new@example.com", UserInfoType.EMAIL);

        verify(userVerificationRepo).save(any(UserVerification.class));
        verify(verificationEmailSender).send("new@example.com", "123456");
    }

    @Test
    void verifyUser_throwsAndDoesNotPersistOrDispatch_whenEmailAlreadyExists() {
        when(userRepository.existsByEmail("dup@example.com")).thenReturn(true);

        assertThatThrownBy(() -> registerService.verifyUser("dup@example.com", UserInfoType.EMAIL))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("Email already registered: dup@example.com");

        verifyNoInteractions(userVerificationRepo, verificationEmailSender);
    }

    @Test
    void verifyUser_doesNotDispatchEmail_whenVerificationTypeIsNotEmail() {
        var nonEmailVerification = UserVerification.builder()
                .userInfo("some-info")
                .userInfoType(null)
                .verificationCode("123456")
                .build();

        when(userRepository.existsByEmail("some-info")).thenReturn(false);
        when(userVerificationRepo.save(any(UserVerification.class))).thenReturn(nonEmailVerification);

        registerService.verifyUser("some-info", UserInfoType.EMAIL);

        verifyNoInteractions(verificationEmailSender);
    }
}
