package finalProject.vote.mapper;

import finalProject.vote.dto.request.VoteCreateAndUpdateRequestDto;
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
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "theory", ignore = true)
    Vote toVote(VoteCreateAndUpdateRequestDto dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "theoryId",source = "theory.id")
    VoteResponseDto  toDto(Vote entity);


    List<VoteResponseDto> toDto(List<Vote> entities);

}
