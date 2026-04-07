package finalProject.contradiction.mapper;

import finalProject.contradiction.dto.request.ContradictionCreateRequestDto;
import finalProject.contradiction.dto.response.ContradictionResponseDto;
import finalProject.contradiction.entity.Contradiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContradictionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "theoryA", ignore = true)
    @Mapping(target = "theoryB", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Contradiction toEntity(ContradictionCreateRequestDto dto);

    @Mapping(target = "theoryAId", source = "theoryA.id")
    @Mapping(target = "theoryATitle", source = "theoryA.title")
    @Mapping(target = "theoryBId", source = "theoryB.id")
    @Mapping(target = "theoryBTitle", source = "theoryB.title")
    @Mapping(target = "userId", source = "user.id")
    ContradictionResponseDto toDto(Contradiction entity);

    List<ContradictionResponseDto> toDtoList(List<Contradiction> entities);
}