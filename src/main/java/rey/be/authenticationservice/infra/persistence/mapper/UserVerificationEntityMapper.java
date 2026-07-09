package rey.be.authenticationservice.infra.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import rey.be.authenticationservice.domain.model.UserVerification;
import rey.be.authenticationservice.infra.persistence.entity.UserVerificationEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserVerificationEntityMapper {

    UserVerificationEntity toEntity(UserVerification userVerification);

    UserVerification toDomain(UserVerificationEntity userVerificationEntity);
}
