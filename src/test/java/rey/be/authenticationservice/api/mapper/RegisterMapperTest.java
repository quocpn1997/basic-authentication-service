package rey.be.authenticationservice.api.mapper;

import org.junit.jupiter.api.Test;
import rey.be.authenticationservice.api.dto.register.RegisterRequest;
import rey.be.authenticationservice.domain.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterMapperTest {

    private final RegisterMapper mapper = new RegisterMapperImpl();

    @Test
    void toDomain_mapsFields_andIgnoresIdAndCreatedAt() {
        var request = new RegisterRequest("john.doe@example.com", "secret12", "John Doe");

        var user = mapper.toDomain(request);

        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPassword()).isEqualTo("secret12");
        assertThat(user.getFullName()).isEqualTo("John Doe");
        assertThat(user.getId()).isNull();
        assertThat(user.getCreatedAt()).isNull();
    }

    @Test
    void toDomain_returnsNull_whenRequestIsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void toResponse_mapsFields_withConstantMessage() {
        var user = User.builder().id(5L).email("john.doe@example.com").fullName("John Doe").password("hashed").build();

        var response = mapper.toResponse(user);

        assertThat(response.id()).isEqualTo(5L);
        assertThat(response.email()).isEqualTo("john.doe@example.com");
        assertThat(response.fullName()).isEqualTo("John Doe");
        assertThat(response.message()).isEqualTo("Registration successful");
    }

    @Test
    void toResponse_returnsNull_whenUserIsNull() {
        assertThat(mapper.toResponse(null)).isNull();
    }
}
