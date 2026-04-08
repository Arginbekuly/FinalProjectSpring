package finalProject.user.controller;

import finalProject.user.dto.request.UserCreateRequestDto;
import finalProject.user.dto.request.UserUpdateRequestDto;
import finalProject.user.dto.response.UserResponseDto;
import finalProject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@Valid @RequestBody UserCreateRequestDto dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/by-email")
    public UserResponseDto getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UserUpdateRequestDto request
    ) {
        return userService.updateUser(userId, request);
    }

    @PatchMapping("/{userId}/status")
    public UserResponseDto changeStatus(@PathVariable UUID userId) {
        return userService.changeStatus(userId);
    }

    @PatchMapping("/{userId}/role")
    public UserResponseDto changeRole(@PathVariable UUID userId) {
        return userService.changeRole(userId);
    }

    @DeleteMapping("/{userId}")
    public UserResponseDto softDeleteUser(@PathVariable UUID userId) {
        return userService.softDeleteUser(userId);
    }
}
