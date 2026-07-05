package rey.be.authenticationservice.infra.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.infra.persistence.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}
