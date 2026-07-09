package rey.be.authenticationservice.infra.persistence.mapper;

import org.junit.jupiter.api.Test;
import rey.be.authenticationservice.domain.model.UserInfoType;
import rey.be.authenticationservice.domain.model.UserVerification;
import rey.be.authenticationservice.infra.persistence.entity.UserVerificationEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserVerificationEntityMapperTest {

    private final UserVerificationEntityMapper mapper = new UserVerificationEntityMapperImpl();

    @Test
    void toEntity_mapsFields_andConvertsEnumToString() {
        var verification = UserVerification.builder()
                .userInfo("john.doe@example.com")
                .userInfoType(UserInfoType.EMAIL)
                .verificationCode("123456")
                .createdAt(LocalDateTime.now())
                .build();

        var entity = mapper.toEntity(verification);

        assertThat(entity.getUserInfo()).isEqualTo("john.doe@example.com");
        assertThat(entity.getUserInfoType()).isEqualTo("EMAIL");
        assertThat(entity.getVerificationCode()).isEqualTo("123456");
    }

    @Test
    void toEntity_returnsNull_whenVerificationIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toEntity_leavesUserInfoTypeNull_whenTypeIsNull() {
        var verification = UserVerification.builder()
                .userInfo("john.doe@example.com")
                .userInfoType(null)
                .verificationCode("123456")
                .build();

        var entity = mapper.toEntity(verification);

        assertThat(entity.getUserInfoType()).isNull();
    }

    @Test
    void toDomain_mapsFields_andConvertsStringToEnum() {
        var createdAt = LocalDateTime.now();
        var entity = UserVerificationEntity.builder()
                .userInfo("john.doe@example.com")
                .userInfoType("EMAIL")
                .verificationCode("654321")
                .createdAt(createdAt)
                .build();

        var verification = mapper.toDomain(entity);

        assertThat(verification.getUserInfo()).isEqualTo("john.doe@example.com");
        assertThat(verification.getUserInfoType()).isEqualTo(UserInfoType.EMAIL);
        assertThat(verification.getVerificationCode()).isEqualTo("654321");
        assertThat(verification.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void toDomain_returnsNull_whenEntityIsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void toDomain_leavesUserInfoTypeNull_whenTypeIsNull() {
        var entity = UserVerificationEntity.builder()
                .userInfo("john.doe@example.com")
                .userInfoType(null)
                .verificationCode("654321")
                .build();

        var verification = mapper.toDomain(entity);

        assertThat(verification.getUserInfoType()).isNull();
    }
}
