package finalProject.theory.dto.request;

import jakarta.validation.constraints.*;

public record TheoryCreateRequestDto(

        @NotBlank(message = "Title cannot be empty")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Description cannot be empty")
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @NotBlank(message = "Summary cannot be empty")
        @Size(max = 500, message = "Summary must not exceed 500 characters")
        String summary

) {
}