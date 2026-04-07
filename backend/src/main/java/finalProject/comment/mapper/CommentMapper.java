package finalProject.comment.mapper;

import finalProject.comment.dto.request.CommentCreateRequestDto;
import finalProject.comment.dto.response.CommentResponseDto;
import finalProject.comment.entity.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user",  ignore = true)
    @Mapping(target = "theory", ignore = true)

    Comment toEntity(CommentCreateRequestDto dto);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "theoryId", source = "theory.id")
    CommentResponseDto toDto(Comment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<CommentResponseDto> toDtoList(List<Comment> entities);

}
