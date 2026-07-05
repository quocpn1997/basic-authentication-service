package rey.be.authenticationservice.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rey.be.authenticationservice.api.dto.register.RegisterRequest;
import rey.be.authenticationservice.api.dto.register.RegisterResponse;
import rey.be.authenticationservice.api.mapper.RegisterMapper;
import rey.be.authenticationservice.domain.service.RegisterService;

@RestController
@RequestMapping("/api/auth/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    private final RegisterMapper registerMapper;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        var user = registerMapper.toDomain(request);
        var saved = registerService.register(user);
        var response = registerMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
