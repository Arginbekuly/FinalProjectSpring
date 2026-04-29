package finalProject.comment.mapper;

import finalProject.comment.dto.request.CommentCreateRequestDto;
import finalProject.comment.dto.request.CommentUpdateRequestDto;
import finalProject.comment.dto.response.CommentResponseDto;
import finalProject.comment.entity.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "userId", ignore = true)

    Comment toComment(CommentCreateRequestDto dto);

    @Mapping(target = "username", expression = "java(null)")
    CommentResponseDto toDto(Comment comments);

    List<CommentResponseDto> toDtoList(List<Comment> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "theoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateCommentFromDto(CommentUpdateRequestDto dto, @MappingTarget Comment comment);

}
