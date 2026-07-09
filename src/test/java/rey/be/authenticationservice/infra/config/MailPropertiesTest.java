package rey.be.authenticationservice.infra.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailPropertiesTest {

    @Test
    void from_roundTripsThroughGetterAndSetter() {
        var properties = new MailProperties();

        properties.setFrom("no-reply@example.com");

        assertThat(properties.getFrom()).isEqualTo("no-reply@example.com");
    }
}
