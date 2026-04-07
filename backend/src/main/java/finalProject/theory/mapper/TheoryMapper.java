package finalProject.theory.mapper;

import finalProject.theory.dto.request.TheoryUpdateRequestDto;
import org.mapstruct.*;
import finalProject.theory.dto.request.TheoryCreateRequestDto;
import finalProject.theory.dto.response.TheoryResponseDto;
import finalProject.theory.entity.Theory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TheoryMapper {
    //Create
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "evidence", ignore = true)
    Theory toEntity(TheoryCreateRequestDto dto);

    //Response
    @Mapping(target = "userId", source = "user.id")
    TheoryResponseDto toDto(Theory theory);

    //List
    List<TheoryResponseDto> toDto(List<Theory> theories);

    //Update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "credibilityScore", ignore = true)
    @Mapping(target = "popularityScore", ignore = true)
    @Mapping(target = "contradictionCount", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "evidence", ignore = true)
    void updateTheoryFromDto(TheoryUpdateRequestDto dto, @MappingTarget Theory theory);
}