package finalProject.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CommentCreateRequestDto(

        @NotNull(message = "Theory ID is required")
        UUID theoryId,

        @NotBlank(message = "Comment text cannot be empty")
        @Size(max = 2000, message = "Comment text must not exceed 2000 characters")
        String text

) {
}