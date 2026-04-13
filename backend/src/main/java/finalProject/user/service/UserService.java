package finalProject.user.service;


import finalProject.common.exception.EmailAlreadyExistsException;
import finalProject.common.exception.NotFoundException;
import finalProject.user.dto.request.UserCreateRequestDto;
import finalProject.user.dto.request.UserUpdateRequestDto;
import finalProject.user.dto.response.UserResponseDto;
import finalProject.user.entity.User;
import finalProject.user.entity.UserRole;
import finalProject.user.entity.UserStatus;
import finalProject.user.mapper.UserMapper;
import finalProject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserCreateRequestDto dto) {
        User user = userMapper.toUser(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserResponseDto getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return userMapper.toDto(user);
    }

    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        return userMapper.toDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();

    }

    public UserResponseDto softDeleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        user.setStatus(UserStatus.DELETED);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserResponseDto updateUser(UUID userId, UserUpdateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        if (request.email() != null && !request.email().isBlank()) {
            if (userRepository.findByEmail(request.email()).isPresent()
                    && !user.getEmail().equals(request.email())) {
                throw new EmailAlreadyExistsException("Email already exists");
            }
            user.setEmail(request.email());
        }

        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }

        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserResponseDto changeStatus(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        if (user.getStatus() == UserStatus.ACTIVE && user.getRole() == UserRole.ADMIN) {
            user.setStatus(UserStatus.BLOCKED);
        } else if (user.getStatus() == UserStatus.BLOCKED && user.getRole() == UserRole.ADMIN) {
            user.setStatus(UserStatus.ACTIVE);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserResponseDto changeRole(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        if (user.getRole() == UserRole.USER) {
            user.setRole(UserRole.ADMIN);
        } else if (user.getRole() == UserRole.ADMIN) {
            user.setRole(UserRole.USER);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

}
