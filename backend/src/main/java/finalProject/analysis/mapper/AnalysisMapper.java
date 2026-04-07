package finalProject.analysis.mapper;

import finalProject.analysis.dto.request.AnalysisCreateRequestDto;
import finalProject.analysis.dto.response.AnalysisResultResponseDto;
import finalProject.analysis.entity.AnalysisResult;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnalysisMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "analyzedAt", ignore = true)
    AnalysisResult toEntity(AnalysisCreateRequestDto dto);

    AnalysisResultResponseDto  toDto(AnalysisResult entity);

    @Mapping(target = "theoryId", source = "theory.id")

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<AnalysisResultResponseDto> toDto(List<AnalysisResult> entities);


}
