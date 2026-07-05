package rey.be.authenticationservice;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class AuthenticationServiceApplicationTests {

    @Test
    void main_delegatesToSpringApplication() {
        var args = new String[]{"--server.port=0"};
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            AuthenticationServiceApplication.main(args);
            springApplication.verify(() -> SpringApplication.run(AuthenticationServiceApplication.class, args));
        }
    }

    @Test
    void constructor_isInstantiable() {
        new AuthenticationServiceApplication();
    }
}
