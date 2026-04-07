package finalProject.theory.dto.response;

import finalProject.theory.entity.TheoryStatus;

import java.util.UUID;

public record TheoryShortResponseDto(
        UUID id,
        String title,
        TheoryStatus status,
        Float credibilityScore,
        Integer viewCount
) {}
