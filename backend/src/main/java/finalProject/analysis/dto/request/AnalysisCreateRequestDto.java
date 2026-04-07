package finalProject.analysis.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AnalysisCreateRequestDto(

        @NotNull(message = "Theory ID is required")
        UUID theoryId

) {
}