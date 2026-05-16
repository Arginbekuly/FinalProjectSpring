package finalProject.evidence.dto.request;

import finalProject.evidence.entity.EvidencePosition;
import finalProject.evidence.entity.EvidenceType;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record EvidenceCreateRequestDto(

        @NotBlank(message = "Title cannot be empty")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Content cannot be empty")
        @Size(max = 5000, message = "Content must not exceed 5000 characters")
        String content,

        @NotBlank(message = "Source name cannot be empty")
        @Size(max = 255, message = "Source name must not exceed 255 characters")
        String sourceName,

        @NotBlank(message = "Source URL cannot be empty")
        String sourceUrl,


        @NotNull(message = "Evidence type is required")
        EvidenceType type,

        @NotNull(message = "Evidence position is required")
        EvidencePosition position,

        @NotNull(message = "Theory ID is required")
        UUID theoryId
) {
}
