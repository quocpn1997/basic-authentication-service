package rey.be.authenticationservice.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import rey.be.authenticationservice.api.dto.register.RegisterRequest;
import rey.be.authenticationservice.api.dto.register.RegisterResponse;
import rey.be.authenticationservice.domain.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegisterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toDomain(RegisterRequest request);

    @Mapping(target = "message", constant = "Registration successful")
    RegisterResponse toResponse(User user);
}
