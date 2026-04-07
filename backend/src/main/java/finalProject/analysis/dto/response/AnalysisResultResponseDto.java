package finalProject.analysis.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnalysisResultResponseDto(

        UUID id,
        UUID theoryId,
        Float evidenceScore,
        Float consistencyScore,
        Float communityScore,
        Float finalCredibilityScore,
        String summary,
        LocalDateTime analyzedAt

) {
}