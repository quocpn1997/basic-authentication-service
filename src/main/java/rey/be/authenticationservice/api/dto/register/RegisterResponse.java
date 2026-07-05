package rey.be.authenticationservice.api.dto.register;

public record RegisterResponse(
        Long id,
        String email,
        String fullName,
        String message
) {
}
