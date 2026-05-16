package finalProject.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequestDto(

        @NotBlank(message = "Username is required")
        @Size(message = "Username must be between 3 and 20 characters", min = 3, max = 50)
        String username,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        @Size(message = "Email must be between 5 and 50 characters", min = 5, max = 50)
        String email,

        @NotBlank(message = "Password is required")
        @Size(message = "Password must be at least 6 characters", min = 6)
        String password
        ){
}
