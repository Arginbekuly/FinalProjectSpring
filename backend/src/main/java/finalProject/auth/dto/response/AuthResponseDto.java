package finalProject.auth.dto.response;

import finalProject.user.entity.User;
import finalProject.user.entity.UserRole;

import java.util.UUID;

public record AuthResponseDto(
        String token,
        UserRole role

) {
}
