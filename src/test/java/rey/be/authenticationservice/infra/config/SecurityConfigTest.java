package rey.be.authenticationservice.infra.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityConfigTest {

    @Test
    void passwordEncoder_isBCryptAndEncodesVerifiably() {
        var encoder = new SecurityConfig().passwordEncoder();

        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
        var hashed = encoder.encode("raw");
        assertThat(hashed).isNotEqualTo("raw");
        assertThat(encoder.matches("raw", hashed)).isTrue();
    }
}
