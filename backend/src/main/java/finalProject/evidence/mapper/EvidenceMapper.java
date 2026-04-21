package finalProject.evidence.mapper;

import finalProject.evidence.dto.request.EvidenceCreateRequestDto;
import finalProject.evidence.dto.request.EvidenceUpdateRequestDto;
import finalProject.evidence.dto.response.EvidenceResponseDto;
import finalProject.evidence.entity.Evidence;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "theory", ignore = true)
    Evidence toEvidence(EvidenceCreateRequestDto dto);

    EvidenceResponseDto toDto(Evidence entity);

    List<EvidenceResponseDto> toDtoList(List<Evidence> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "theory", ignore = true)
    void updateEvidenceFromDto(EvidenceUpdateRequestDto dto, @MappingTarget Evidence evidence);

}
