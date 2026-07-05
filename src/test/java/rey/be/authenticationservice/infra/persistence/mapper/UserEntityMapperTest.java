package rey.be.authenticationservice.infra.persistence.mapper;

import org.junit.jupiter.api.Test;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.infra.persistence.entity.UserEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityMapperTest {

    private final UserEntityMapper mapper = new UserEntityMapperImpl();

    @Test
    void toEntity_mapsFields_andIgnoresCreatedAt() {
        var user = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("hashed")
                .fullName("John Doe")
                .createdAt(LocalDateTime.now())
                .build();

        var entity = mapper.toEntity(user);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(entity.getPassword()).isEqualTo("hashed");
        assertThat(entity.getFullName()).isEqualTo("John Doe");
        assertThat(entity.getCreatedAt()).isNull();
    }

    @Test
    void toEntity_returnsNull_whenUserIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toDomain_mapsAllFields() {
        var createdAt = LocalDateTime.now();
        var entity = UserEntity.builder()
                .id(2L)
                .email("john.doe@example.com")
                .password("hashed")
                .fullName("John Doe")
                .createdAt(createdAt)
                .build();

        var user = mapper.toDomain(entity);

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPassword()).isEqualTo("hashed");
        assertThat(user.getFullName()).isEqualTo("John Doe");
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void toDomain_returnsNull_whenEntityIsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }
}
