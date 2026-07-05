package rey.be.authenticationservice.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rey.be.authenticationservice.domain.exception.EmailAlreadyExistsException;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.domain.repository.UserRepository;
import rey.be.authenticationservice.domain.security.PasswordHasher;

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
    private PasswordHasher passwordHasher;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void register_normalizesHashesAndPersists_whenEmailIsNew() {
        var user = User.builder()
                .email("  New@Example.COM ")
                .fullName("  Jane Doe  ")
                .password("raw")
                .build();
        var persisted = User.builder().id(1L).email("new@example.com").fullName("Jane Doe").password("hashed").build();

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordHasher.hash("raw")).thenReturn("hashed");
        when(userRepository.save(user)).thenReturn(persisted);

        var result = registerService.register(user);

        assertThat(result).isSameAs(persisted);
        assertThat(user.getEmail()).isEqualTo("new@example.com");
        assertThat(user.getFullName()).isEqualTo("Jane Doe");
        assertThat(user.getPassword()).isEqualTo("hashed");
        verify(userRepository).save(user);
    }

    @Test
    void register_throwsAndDoesNotPersist_whenEmailAlreadyExists() {
        var user = User.builder()
                .email("Dup@Example.com")
                .fullName("Dup")
                .password("raw")
                .build();

        when(userRepository.existsByEmail("dup@example.com")).thenReturn(true);

        assertThatThrownBy(() -> registerService.register(user))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("Email already registered: dup@example.com");

        verify(userRepository, never()).save(any());
        verifyNoInteractions(passwordHasher);
    }
}
