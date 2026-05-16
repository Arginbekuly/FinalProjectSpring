package finalProject.vote.dto.request;

import finalProject.vote.entity.VoteType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VoteCreateRequestDto(
        @NotNull(message = "Theory ID is required")
        UUID theoryId,

        @NotNull(message = "Vote type is required")
        VoteType type

) {
}
