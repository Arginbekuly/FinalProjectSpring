package finalProject.auth.dto.response;

import finalProject.user.entity.UserRole;

public record AuthResponseDto(

        String token,
        UserRole role

) {
}
