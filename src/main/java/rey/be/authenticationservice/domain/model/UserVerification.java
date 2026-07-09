package rey.be.authenticationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserVerification {

    private String userInfo;

    private UserInfoType userInfoType;

    private String verificationCode;

    private LocalDateTime createdAt;

    public UserVerification(String userInfo, UserInfoType userInfoType) {
        this.userInfo = userInfo;
        this.userInfoType = userInfoType;
        this.verificationCode = generateVerificationCode();
        this.createdAt = LocalDateTime.now();
    }

    private String generateVerificationCode() {
        return switch (userInfoType) {
            case EMAIL -> generateEmailVerificationCode();
            case null -> throw new IllegalArgumentException("userInfoType must not be null");
        };
    }

    private String generateEmailVerificationCode() {
        SecureRandom secureRandom = new SecureRandom();
        int randomNumber = secureRandom.nextInt(1000000);
        return String.format("%06d", randomNumber);
    }
}
