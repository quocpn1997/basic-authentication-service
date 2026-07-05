package rey.be.authenticationservice.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailAlreadyExistsExceptionTest {

    @Test
    void message_includesTheOffendingEmail() {
        var exception = new EmailAlreadyExistsException("user@example.com");

        assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email already registered: user@example.com");
    }
}
