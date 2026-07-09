package rey.be.authenticationservice.infra.config;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.assertj.core.api.Assertions.assertThat;

class AsyncConfigTest {

    @Test
    void emailTaskExecutor_isConfiguredWithPoolSettings() {
        var executor = new AsyncConfig().emailTaskExecutor();

        assertThat(executor).isInstanceOf(ThreadPoolTaskExecutor.class);
        assertThat(executor.getCorePoolSize()).isEqualTo(2);
        assertThat(executor.getMaxPoolSize()).isEqualTo(5);
        assertThat(executor.getThreadNamePrefix()).isEqualTo("verification-email-");
    }
}
