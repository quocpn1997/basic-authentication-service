package rey.be.authenticationservice.api.dto.register;

import org.junit.jupiter.api.Test;
import rey.be.authenticationservice.domain.model.UserInfoType;

import static org.assertj.core.api.Assertions.assertThat;

class VerifyUserDtoTest {

    @Test
    void verifyUserRequest_hasValueSemantics() {
        var request1 = new VerifyUserRequest("john.doe@example.com", UserInfoType.EMAIL);
        var request2 = new VerifyUserRequest("john.doe@example.com", UserInfoType.EMAIL);

        assertThat(request1.userInfo()).isEqualTo("john.doe@example.com");
        assertThat(request1.userInfoType()).isEqualTo(UserInfoType.EMAIL);
        assertThat(request1).isEqualTo(request2).hasSameHashCodeAs(request2);
        assertThat(request1.toString()).contains("john.doe@example.com", "EMAIL");
    }

    @Test
    void verifyUserResponse_hasValueSemantics() {
        var response1 = new VerifyUserResponse("Verification code sent", "j***@example.com");
        var response2 = new VerifyUserResponse("Verification code sent", "j***@example.com");

        assertThat(response1.message()).isEqualTo("Verification code sent");
        assertThat(response1.maskedDestination()).isEqualTo("j***@example.com");
        assertThat(response1).isEqualTo(response2).hasSameHashCodeAs(response2);
        assertThat(response1.toString()).contains("Verification code sent", "j***@example.com");
    }

    @Test
    void sent_masksLocalPart_keepingFirstCharacterAndDomain() {
        var response = VerifyUserResponse.sent("john.doe@example.com");

        assertThat(response.message()).isEqualTo("Verification code sent");
        assertThat(response.maskedDestination()).isEqualTo("j***@example.com");
    }

    @Test
    void sent_masksEntireLocalPart_whenItIsASingleCharacter() {
        var response = VerifyUserResponse.sent("a@example.com");

        assertThat(response.maskedDestination()).isEqualTo("***@example.com");
    }

    @Test
    void sent_masksEverything_whenDestinationHasNoAtSign() {
        var response = VerifyUserResponse.sent("not-an-email");

        assertThat(response.maskedDestination()).isEqualTo("***");
    }
}
