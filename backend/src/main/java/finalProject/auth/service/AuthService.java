package finalProject.auth.service;


import finalProject.auth.dto.request.LoginRequestDto;
import finalProject.auth.dto.request.RegisterRequestDto;
import finalProject.auth.dto.response.AuthResponseDto;
import finalProject.common.exception.EmailAlreadyExistsException;
import finalProject.common.exception.NotFoundException;
import finalProject.user.entity.User;
import finalProject.user.entity.UserRole;
import finalProject.user.entity.UserStatus;
import finalProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email is already in use");
        }

        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        return new AuthResponseDto(jwtService.generateToken(savedUser), savedUser.getRole());
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new NotFoundException("User not found with email: " + dto.email()));
        return new AuthResponseDto(jwtService.generateToken(user), user.getRole());
    }
}
