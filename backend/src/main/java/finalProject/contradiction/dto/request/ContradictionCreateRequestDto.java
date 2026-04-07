package finalProject.contradiction.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ContradictionCreateRequestDto(

        @NotNull(message = "Theory ID is required")
        UUID theoryId,

        @NotNull(message = "Contradicting theory ID is required")
        UUID contradictingTheoryId,

        @NotBlank(message = "Reason cannot be empty")
        @Size(max = 2000, message = "Reason must not exceed 2000 characters")
        String reason

) {
}