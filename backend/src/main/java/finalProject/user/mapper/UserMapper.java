package finalProject.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import finalProject.user.dto.request.UserCreateRequestDto;
import finalProject.user.dto.response.UserResponseDto;
import finalProject.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "theories", ignore = true)

    User toUser(UserCreateRequestDto userDto);
    UserResponseDto toDto(User user);
}
