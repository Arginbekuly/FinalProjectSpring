package finalProject.theory.dto.response;

import finalProject.theory.entity.TheoryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TheoryResponseDto(
        UUID id,
        UUID userId,
        String title,
        String summary,
        TheoryStatus status,
        Float credibilityScore,
        Float popularityScore,
        Integer contradictionCount,
        Integer viewCount,
        LocalDateTime createdAt
) {}
