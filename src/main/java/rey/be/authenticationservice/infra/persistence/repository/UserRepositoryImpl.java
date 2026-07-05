package rey.be.authenticationservice.infra.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.domain.repository.UserRepository;
import rey.be.authenticationservice.infra.persistence.mapper.UserEntityMapper;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    private final UserEntityMapper userEntityMapper;

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        var entity = userEntityMapper.toEntity(user);
        var saved = userJpaRepository.save(entity);
        return userEntityMapper.toDomain(saved);
    }
}
