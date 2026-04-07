package finalProject.theory.dto.response;

import finalProject.theory.entity.TheoryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TheoryDetailsResponseDto(
        UUID id,
        UUID userId,
        String title,
        String description,
        String summary,
        TheoryStatus status,
        Float credibilityScore,
        Float popularityScore,
        Integer contradictionCount,
        Integer viewCount,
        Integer evidenceCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime publishedAt
) {}