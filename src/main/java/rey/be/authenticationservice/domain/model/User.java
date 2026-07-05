package rey.be.authenticationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import rey.be.authenticationservice.domain.security.PasswordHasher;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String email;

    private String password;

    private String fullName;

    private LocalDateTime createdAt;

    public void normalize() {
        this.email = email.trim().toLowerCase();
        this.fullName = fullName.trim();
    }

    public void hashPassword(PasswordHasher passwordHasher) {
        this.password = passwordHasher.hash(password);
    }
}
