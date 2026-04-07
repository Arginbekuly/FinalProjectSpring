package finalProject.theory.dto.request;

import jakarta.validation.constraints.*;

public record TheoryCreateRequestDto(

        @NotBlank(message = "Title cannot be empty")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Description cannot be empty")
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @NotBlank(message = "Summary cannot be empty")
        @Size(max = 500, message = "Summary must not exceed 500 characters")
        String summary,

        @NotNull(message = "Credibility score is required")
        @DecimalMin(value = "0.0", message = "Credibility score must be at least 0.0")
        @DecimalMax(value = "1.0", message = "Credibility score must be at most 1.0")
        Float credibilityScore,

        @NotNull(message = "Popularity score is required")
        @DecimalMin(value = "0.0", message = "Popularity score must be at least 0.0")
        @DecimalMax(value = "1.0", message = "Popularity score must be at most 1.0")
        Float popularityScore,

        @NotNull(message = "Contradiction count is required")
        @Min(value = 0, message = "Contradiction count cannot be negative")
        Integer contradictionCount,

        @NotNull(message = "View count is required")
        @Min(value = 0, message = "View count cannot be negative")
        Integer viewCount

) {
}