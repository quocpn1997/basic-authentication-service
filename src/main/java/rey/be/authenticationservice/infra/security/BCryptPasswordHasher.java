package rey.be.authenticationservice.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rey.be.authenticationservice.domain.security.PasswordHasher;

@Component
@RequiredArgsConstructor
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
