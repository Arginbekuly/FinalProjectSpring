package finalProject.theory.mapper;

import finalProject.theory.dto.request.TheoryUpdateRequestDto;
import finalProject.theory.dto.request.TheoryCreateRequestDto;
import finalProject.theory.dto.response.TheoryResponseDto;
import finalProject.theory.entity.Theory;
import finalProject.theory.entity.TheoryStatus;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TheoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "evidence", ignore = true)
    @Mapping(target = "status", expression = "java(TheoryStatus.PENDING_REVIEW)")
    @Mapping(target = "credibilityScore", expression = "java(0f)")
    @Mapping(target = "popularityScore", expression = "java(0f)")
    @Mapping(target = "supportScore", expression = "java(0f)")
    @Mapping(target = "oppositionScore", expression = "java(0f)")
    @Mapping(target = "controversyScore", expression = "java(0f)")
    @Mapping(target = "investigationStatus", expression = "java(finalProject.theory.entity.InvestigationStatus.SUBMITTED)")
    @Mapping(target = "debateVerdict", expression = "java(finalProject.theory.entity.DebateVerdict.NOT_ENOUGH_DATA)")
    @Mapping(target = "contradictionCount", constant = "0")
    @Mapping(target = "viewCount", constant = "0")
    Theory toTheory(TheoryCreateRequestDto theoryDto);

    TheoryResponseDto toDto(Theory theory);

    List<TheoryResponseDto> toDto(List<Theory> theories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "credibilityScore", ignore = true)
    @Mapping(target = "popularityScore", ignore = true)
    @Mapping(target = "supportScore", ignore = true)
    @Mapping(target = "oppositionScore", ignore = true)
    @Mapping(target = "controversyScore", ignore = true)
    @Mapping(target = "investigationStatus", ignore = true)
    @Mapping(target = "debateVerdict", ignore = true)
    @Mapping(target = "contradictionCount", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "evidence", ignore = true)
    void updateTheoryFromDto(TheoryUpdateRequestDto dto, @MappingTarget Theory theory);
}
