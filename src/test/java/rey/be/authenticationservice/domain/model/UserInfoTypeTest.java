package rey.be.authenticationservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoTypeTest {

    @Test
    void valueOf_andValues_exposeEmailConstant() {
        assertThat(UserInfoType.valueOf("EMAIL")).isEqualTo(UserInfoType.EMAIL);
        assertThat(UserInfoType.values()).containsExactly(UserInfoType.EMAIL);
    }
}
