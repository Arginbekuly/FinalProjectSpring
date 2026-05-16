package finalProject.contradiction.dto.response;

import finalProject.contradiction.entity.ContradictionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContradictionResponseDto(

        UUID id,
        UUID theoryId,
        String theoryTitle,
        UUID contradictingTheoryId,
        String contradictingTheoryTitle,
        String reason,
        ContradictionStatus status,
        LocalDateTime createdAt

) {
}