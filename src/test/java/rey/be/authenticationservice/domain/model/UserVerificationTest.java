package rey.be.authenticationservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserVerificationTest {

    @Test
    void constructor_generatesSixDigitCode_andTimestamp_forEmailType() {
        var verification = new UserVerification("john.doe@example.com", UserInfoType.EMAIL);

        assertThat(verification.getUserInfo()).isEqualTo("john.doe@example.com");
        assertThat(verification.getUserInfoType()).isEqualTo(UserInfoType.EMAIL);
        assertThat(verification.getVerificationCode()).matches("\\d{6}");
        assertThat(verification.getCreatedAt()).isNotNull();
    }

    @Test
    void constructor_throws_whenUserInfoTypeIsNotSupported() {
        assertThatThrownBy(() -> new UserVerification("john.doe@example.com", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("userInfoType must not be null");
    }
}
