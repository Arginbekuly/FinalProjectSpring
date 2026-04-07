package finalProject.evidence.dto.response;

import finalProject.evidence.entity.EvidenceType;

import java.time.LocalDateTime;
import java.util.UUID;

public record EvidenceResponseDto(

        UUID id,
        UUID theoryId,
        UUID userId,
        EvidenceType type,
        String title,
        String content,
        String sourceUrl,
        String sourceName,
        Integer reliabilityScore,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
