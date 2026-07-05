package rey.be.authenticationservice.infra.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BCryptPasswordHasherTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private BCryptPasswordHasher passwordHasher;

    @Test
    void hash_delegatesToPasswordEncoder() {
        when(passwordEncoder.encode("raw")).thenReturn("hashed");

        assertThat(passwordHasher.hash("raw")).isEqualTo("hashed");
    }
}
