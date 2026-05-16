package finalProject.theory.dto.response;

import finalProject.theory.entity.DebateVerdict;
import finalProject.theory.entity.InvestigationStatus;
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
        Float supportScore,
        Float oppositionScore,
        Float controversyScore,
        InvestigationStatus investigationStatus,
        DebateVerdict debateVerdict,
        Integer contradictionCount,
        Integer viewCount,
        LocalDateTime createdAt
) {}
