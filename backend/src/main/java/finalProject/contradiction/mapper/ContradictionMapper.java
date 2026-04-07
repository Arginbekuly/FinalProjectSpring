package finalProject.contradiction.mapper;


import finalProject.contradiction.dto.request.ContradictionCreateRequestDto;
import finalProject.contradiction.dto.response.ContradictionResponseDto;
import finalProject.contradiction.entity.Contradiction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContradictionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Contradiction toEntity(ContradictionCreateRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContradictionResponseDto toDto(Contradiction entity);
    List<ContradictionResponseDto> toDtoList(List<Contradiction> entities);
}
