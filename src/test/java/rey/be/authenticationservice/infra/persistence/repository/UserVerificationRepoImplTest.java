package rey.be.authenticationservice.infra.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rey.be.authenticationservice.domain.model.UserInfoType;
import rey.be.authenticationservice.domain.model.UserVerification;
import rey.be.authenticationservice.infra.persistence.entity.UserVerificationEntity;
import rey.be.authenticationservice.infra.persistence.mapper.UserVerificationEntityMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserVerificationRepoImplTest {

    @Mock
    private UserVerificationEntityMapper emailVerificationEntityMapper;

    @Mock
    private UserVerificationJpaRepository userVerificationJpaRepository;

    @InjectMocks
    private UserVerificationRepoImpl userVerificationRepoImpl;

    @Test
    void save_mapsToEntity_persists_andMapsBackToDomain() {
        var domain = UserVerification.builder()
                .userInfo("john.doe@example.com")
                .userInfoType(UserInfoType.EMAIL)
                .verificationCode("123456")
                .build();
        var entity = UserVerificationEntity.builder().userInfo("john.doe@example.com").build();
        var savedEntity = UserVerificationEntity.builder().userInfo("john.doe@example.com").build();
        var savedDomain = UserVerification.builder().userInfo("john.doe@example.com").build();

        when(emailVerificationEntityMapper.toEntity(domain)).thenReturn(entity);
        when(userVerificationJpaRepository.save(entity)).thenReturn(savedEntity);
        when(emailVerificationEntityMapper.toDomain(savedEntity)).thenReturn(savedDomain);

        assertThat(userVerificationRepoImpl.save(domain)).isSameAs(savedDomain);
    }
}
