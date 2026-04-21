package finalProject.evidence.dto.request;

import finalProject.evidence.entity.EvidenceType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record EvidenceUpdateRequestDto(

        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @Size(max = 5000, message = "Content must not exceed 5000 characters")
        String content,

        @Size(max = 255, message = "Source name must not exceed 255 characters")
        String sourceName,

        String sourceUrl,

        @Min(value = 0, message = "Reliability score must be at least 0")
        @Max(value = 100, message = "Reliability score must be at most 100")
        Integer reliabilityScore,

        EvidenceType type
) {
}
