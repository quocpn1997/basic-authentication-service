package rey.be.authenticationservice.api.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rey.be.authenticationservice.domain.model.UserInfoType;

public record VerifyUserRequest(

        @NotBlank(message = "User info is required")
        String userInfo,

        @NotNull
        UserInfoType userInfoType
) {
}
