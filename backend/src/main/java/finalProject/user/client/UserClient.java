package finalProject.user.client;

import finalProject.config.FeignAuthConfig;
import finalProject.user.dto.request.UserCreateRequestDto;
import finalProject.user.dto.request.UserUpdateRequestDto;
import finalProject.user.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service", url = "${services.user.url}", path = "/api/v1/users", configuration = FeignAuthConfig.class)
public interface UserClient {

    @PostMapping("/regUser")
    UserResponseDto createUser(@Valid @RequestBody UserCreateRequestDto dto);

    @GetMapping("/{userId}")
    UserResponseDto getUserById(@PathVariable UUID userId);

    @GetMapping("/getByEmail")
    UserResponseDto getUserByEmail(@RequestParam("email") String email);

    @GetMapping("/getAllUsers")
    List<UserResponseDto> getAllUsers();

    @PutMapping("/{userId}")
    UserResponseDto updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UserUpdateRequestDto request
    );

    @PatchMapping("/{userId}/status")
    UserResponseDto changeStatus(@PathVariable UUID userId);

    @PatchMapping("/{userId}/role")
    UserResponseDto changeRole(@PathVariable UUID userId);

    @DeleteMapping("/{userId}")
    UserResponseDto softDeleteUser(@PathVariable UUID userId);
}
