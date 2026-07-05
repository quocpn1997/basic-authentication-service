package rey.be.authenticationservice.infra.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.infra.persistence.entity.UserEntity;
import rey.be.authenticationservice.infra.persistence.mapper.UserEntityMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    void existsByEmail_delegatesToJpaRepository() {
        when(userJpaRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        assertThat(userRepositoryImpl.existsByEmail("john.doe@example.com")).isTrue();
    }

    @Test
    void save_mapsToEntity_persists_andMapsBackToDomain() {
        var domain = User.builder().email("john.doe@example.com").build();
        var entity = UserEntity.builder().email("john.doe@example.com").build();
        var savedEntity = UserEntity.builder().id(1L).email("john.doe@example.com").build();
        var savedDomain = User.builder().id(1L).email("john.doe@example.com").build();

        when(userEntityMapper.toEntity(domain)).thenReturn(entity);
        when(userJpaRepository.save(entity)).thenReturn(savedEntity);
        when(userEntityMapper.toDomain(savedEntity)).thenReturn(savedDomain);

        assertThat(userRepositoryImpl.save(domain)).isSameAs(savedDomain);
    }
}
