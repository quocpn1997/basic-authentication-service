package rey.be.authenticationservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rey.be.authenticationservice.domain.exception.EmailAlreadyExistsException;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.domain.model.UserInfoType;
import rey.be.authenticationservice.domain.model.UserVerification;
import rey.be.authenticationservice.domain.repository.UserRepository;
import rey.be.authenticationservice.domain.repository.UserVerificationRepo;
import rey.be.authenticationservice.domain.security.PasswordHasher;
import rey.be.authenticationservice.infra.mail.VerificationEmailSender;

import static rey.be.authenticationservice.domain.model.UserInfoType.EMAIL;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    private final UserVerificationRepo userVerificationRepo;

    private final PasswordHasher passwordHasher;

    private final VerificationEmailSender verificationEmailSender;

    @Transactional
    public User registerUser(User user) {
        user.normalize();

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        user.hashPassword(passwordHasher);

        return userRepository.save(user);
    }

    public void verifyUser(String userInfo, UserInfoType userInfoType) {
        if (userRepository.existsByEmail(userInfo)) {
            throw new EmailAlreadyExistsException(userInfo);
        }

        var verification = userVerificationRepo.save(new UserVerification(userInfo, userInfoType));

        sendUserVerification(verification);
    }

    private void sendUserVerification(UserVerification userVerification) {
        if (EMAIL.equals(userVerification.getUserInfoType())) {
            verificationEmailSender.send(userVerification.getUserInfo(), userVerification.getVerificationCode());
        }
    }
}
