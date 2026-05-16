package finalProject.vote.dto.response;

import finalProject.vote.entity.VoteType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoteResponseDto(

        UUID id,
        UUID userId,
        UUID theoryId,
        VoteType type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}