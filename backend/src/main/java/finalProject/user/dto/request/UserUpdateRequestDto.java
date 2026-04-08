package finalProject.user.dto.request;



public record UserUpdateRequestDto (
    String email,
    String username,
    String password
){
}