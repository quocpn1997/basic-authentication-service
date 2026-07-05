package rey.be.authenticationservice.domain.model;

import org.junit.jupiter.api.Test;
import rey.be.authenticationservice.domain.security.PasswordHasher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTest {

    @Test
    void normalize_trimsAndLowercasesEmail_andTrimsFullName() {
        var user = User.builder()
                .email("  John.Doe@Example.COM ")
                .fullName("  John Doe  ")
                .password("raw")
                .build();

        user.normalize();

        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getFullName()).isEqualTo("John Doe");
        assertThat(user.getPassword()).isEqualTo("raw");
    }

    @Test
    void hashPassword_replacesPasswordWithHashedValue() {
        var hasher = mock(PasswordHasher.class);
        when(hasher.hash("raw")).thenReturn("hashed");
        var user = User.builder().password("raw").build();

        user.hashPassword(hasher);

        assertThat(user.getPassword()).isEqualTo("hashed");
    }
}
