package finalProject.evidence.mapper;

import finalProject.evidence.dto.request.EvidenceCreateRequestDto;
import finalProject.evidence.dto.response.EvidenceResponseDto;
import finalProject.evidence.entity.Evidence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "theory", ignore = true)
    Evidence toEntity(EvidenceCreateRequestDto dto);

    EvidenceResponseDto toDto(Evidence entity);

    List<EvidenceResponseDto> toDtoList(List<Evidence> entities);
}
