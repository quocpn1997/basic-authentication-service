package rey.be.authenticationservice.api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rey.be.authenticationservice.domain.exception.EmailAlreadyExistsException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleEmailAlreadyExists_returnsConflictBody() {
        var exception = new EmailAlreadyExistsException("john.doe@example.com");

        var response = handler.handleEmailAlreadyExists(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody())
                .containsEntry("status", 409)
                .containsEntry("error", "Conflict")
                .containsEntry("message", "Email already registered: john.doe@example.com")
                .containsKey("timestamp");
    }

    @Test
    @SuppressWarnings("unchecked")
    void handleValidation_returnsBadRequestWithFieldErrors() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(new FieldError("registerRequest", "email", "Email is required")));
        var exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        var response = handler.handleValidation(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        var body = response.getBody();
        assertThat(body)
                .containsEntry("status", 400)
                .containsEntry("error", "Bad Request")
                .containsEntry("message", "Validation failed")
                .containsKey("timestamp");
        assertThat((Map<String, String>) body.get("fields")).containsEntry("email", "Email is required");
    }
}
