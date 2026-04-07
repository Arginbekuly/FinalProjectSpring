package finalProject.evidence.dto.request;

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

        @NotNull(message = "Reliability score is required")
        @Min(value = 0, message = "Reliability score must be at least 0")
        @Max(value = 100, message = "Reliability score must be at most 100")
        Integer reliabilityScore,

        @NotNull(message = "Theory ID is required")
        UUID theoryId
) {
}