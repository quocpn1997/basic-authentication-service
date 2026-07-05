package rey.be.authenticationservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rey.be.authenticationservice.domain.exception.EmailAlreadyExistsException;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.domain.repository.UserRepository;
import rey.be.authenticationservice.domain.security.PasswordHasher;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    private final PasswordHasher passwordHasher;

    @Transactional
    public User register(User user) {
        user.normalize();

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        user.hashPassword(passwordHasher);

        return userRepository.save(user);
    }
}
