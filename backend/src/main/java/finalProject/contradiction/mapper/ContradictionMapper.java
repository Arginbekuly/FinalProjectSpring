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
    @Mapping(target = "severity", source = "severity")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "theoryIdA", source = "theoryId")
    @Mapping(target = "theoryIdB", source = "contradictingTheoryId")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Contradiction toEntity(ContradictionCreateRequestDto dto);

    @Mapping(target = "theoryId", source = "theoryIdA")
    @Mapping(target = "theoryTitle", expression = "java(null)")
    @Mapping(target = "contradictingTheoryId", source = "theoryIdB")
    @Mapping(target = "contradictingTheoryTitle", expression = "java(null)")
    ContradictionResponseDto toDto(Contradiction entity);

    List<ContradictionResponseDto> toDtoList(List<Contradiction> entities);
}
