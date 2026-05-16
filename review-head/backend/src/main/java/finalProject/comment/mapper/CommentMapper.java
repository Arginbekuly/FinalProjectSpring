package finalProject.comment.mapper;

import finalProject.comment.dto.request.CommentCreateRequestDto;
import finalProject.comment.dto.response.CommentResponseDto;
import finalProject.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "userId", ignore = true)

    Comment toEntity(CommentCreateRequestDto dto);

    @Mapping(target = "username", expression = "java(null)")
    CommentResponseDto toDto(Comment entity);

    List<CommentResponseDto> toDtoList(List<Comment> entities);

}
