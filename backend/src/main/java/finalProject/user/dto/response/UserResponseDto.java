package finalProject.user.dto.response;

import finalProject.user.entity.UserRole;
import finalProject.user.entity.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username,
        String email,
        UserRole role,
        UserStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
