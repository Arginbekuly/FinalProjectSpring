package finalProject.comment.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDto(

        UUID id,
        UUID userId,
        String username,
        UUID theoryId,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}