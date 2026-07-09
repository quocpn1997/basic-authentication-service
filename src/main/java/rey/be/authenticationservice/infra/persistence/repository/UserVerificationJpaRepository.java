package rey.be.authenticationservice.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rey.be.authenticationservice.infra.persistence.entity.UserVerificationEntity;

@Repository
public interface UserVerificationJpaRepository extends JpaRepository<UserVerificationEntity, String> {
}
