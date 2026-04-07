package finalProject.vote.mapper;

import finalProject.vote.dto.request.VoteCreateRequestDto;
import finalProject.vote.dto.response.VoteResponseDto;
import finalProject.vote.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target= "createdAt",  ignore = true)
    @Mapping(target = "updatedAt", ignore = true)


    Vote toEntity(VoteCreateRequestDto dto);
    VoteResponseDto  toDto(Vote entity);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "theoryId",source = "theory.id")
    List<VoteResponseDto> toDto(List<Vote> entities);

}
