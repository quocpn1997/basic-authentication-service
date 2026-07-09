package rey.be.authenticationservice.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import rey.be.authenticationservice.api.dto.register.RegisterRequest;
import rey.be.authenticationservice.api.dto.register.RegisterResponse;
import rey.be.authenticationservice.api.dto.register.VerifyUserRequest;
import rey.be.authenticationservice.api.dto.register.VerifyUserResponse;
import rey.be.authenticationservice.api.mapper.RegisterMapper;
import rey.be.authenticationservice.domain.model.User;
import rey.be.authenticationservice.domain.model.UserInfoType;
import rey.be.authenticationservice.domain.service.RegisterService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock
    private RegisterService registerService;

    @Mock
    private RegisterMapper registerMapper;

    @InjectMocks
    private RegisterController registerController;

    @Test
    void register_returnsCreatedWithMappedResponse() {
        var request = new RegisterRequest("john.doe@example.com", "password1", "John Doe");
        var domain = User.builder().email("john.doe@example.com").build();
        var saved = User.builder().id(1L).email("john.doe@example.com").build();
        var response = new RegisterResponse(1L, "john.doe@example.com", "John Doe", "Registration successful");

        when(registerMapper.toDomain(request)).thenReturn(domain);
        when(registerService.registerUser(domain)).thenReturn(saved);
        when(registerMapper.toResponse(saved)).thenReturn(response);

        var result = registerController.register(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isSameAs(response);
    }

    @Test
    void verifyUser_delegatesToService_andReturnsCreated() {
        var request = new VerifyUserRequest("john.doe@example.com", UserInfoType.EMAIL);

        var result = registerController.verifyUser(request);

        verify(registerService).verifyUser("john.doe@example.com", UserInfoType.EMAIL);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(VerifyUserResponse.sent("john.doe@example.com"));
    }
}
