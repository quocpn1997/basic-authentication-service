package rey.be.authenticationservice.domain.security;

public interface PasswordHasher {

    String hash(String rawPassword);
}
