package rey.be.authenticationservice.infra.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rey.be.authenticationservice.domain.model.UserVerification;
import rey.be.authenticationservice.domain.repository.UserVerificationRepo;
import rey.be.authenticationservice.infra.persistence.mapper.UserVerificationEntityMapper;

@Component
@RequiredArgsConstructor
public class UserVerificationRepoImpl implements UserVerificationRepo {

    private final UserVerificationEntityMapper emailVerificationEntityMapper;

    private final UserVerificationJpaRepository userVerificationJpaRepository;

    @Override
    public UserVerification save(UserVerification userVerification) {
        var entity = emailVerificationEntityMapper.toEntity(userVerification);
        var saved = userVerificationJpaRepository.save(entity);
        return emailVerificationEntityMapper.toDomain(saved);
    }
}
