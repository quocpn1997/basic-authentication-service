package rey.be.authenticationservice.api.dto.register;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterDtoTest {

    @Test
    void registerRequest_hasValueSemantics() {
        var request1 = new RegisterRequest("john.doe@example.com", "password1", "John Doe");
        var request2 = new RegisterRequest("john.doe@example.com", "password1", "John Doe");

        assertThat(request1.email()).isEqualTo("john.doe@example.com");
        assertThat(request1.password()).isEqualTo("password1");
        assertThat(request1.fullName()).isEqualTo("John Doe");
        assertThat(request1).isEqualTo(request2).hasSameHashCodeAs(request2);
        assertThat(request1.toString()).contains("john.doe@example.com", "John Doe");
    }

    @Test
    void registerResponse_hasValueSemantics() {
        var response1 = new RegisterResponse(1L, "john.doe@example.com", "John Doe", "Registration successful");
        var response2 = new RegisterResponse(1L, "john.doe@example.com", "John Doe", "Registration successful");

        assertThat(response1.id()).isEqualTo(1L);
        assertThat(response1.email()).isEqualTo("john.doe@example.com");
        assertThat(response1.fullName()).isEqualTo("John Doe");
        assertThat(response1.message()).isEqualTo("Registration successful");
        assertThat(response1).isEqualTo(response2).hasSameHashCodeAs(response2);
        assertThat(response1.toString()).contains("john.doe@example.com", "Registration successful");
    }
}
