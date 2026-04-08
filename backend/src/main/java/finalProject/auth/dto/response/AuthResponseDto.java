package finalProject.auth.dto.response;

import java.util.UUID;

public record AuthResponseDto(

        UUID userId,
        String username,
        String email,
        String token,
        String tokenType

) {
}
