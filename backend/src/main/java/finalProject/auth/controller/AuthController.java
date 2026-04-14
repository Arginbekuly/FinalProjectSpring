package finalProject.auth.controller;


import finalProject.auth.dto.request.LoginRequestDto;
import finalProject.auth.dto.request.RegisterRequestDto;
import finalProject.auth.dto.response.AuthResponseDto;
import finalProject.auth.service.AuthService;
import finalProject.user.dto.response.UserResponseDto;
import finalProject.user.entity.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponseDto me(@AuthenticationPrincipal User user) {
        return authService.getCurrentUser(user);
    }

}
