package rey.be.authenticationservice.domain.repository;

import rey.be.authenticationservice.domain.model.User;

public interface UserRepository {

    boolean existsByEmail(String email);

    User save(User user);
}
