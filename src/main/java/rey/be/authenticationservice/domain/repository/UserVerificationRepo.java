package rey.be.authenticationservice.domain.repository;

import rey.be.authenticationservice.domain.model.UserVerification;

public interface UserVerificationRepo {

    UserVerification save(UserVerification userVerification);
}
