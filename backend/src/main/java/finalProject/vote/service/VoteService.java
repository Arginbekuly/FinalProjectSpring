package finalProject.vote.service;

import finalProject.common.exception.ForbiddenException;
import finalProject.common.exception.NotFoundException;
import finalProject.theory.entity.Theory;
import finalProject.theory.repository.TheoryRepository;
import finalProject.user.entity.User;
import finalProject.vote.dto.request.VoteCreateAndUpdateRequestDto;
import finalProject.vote.dto.response.VoteResponseDto;
import finalProject.vote.entity.Vote;
import finalProject.vote.mapper.VoteMapper;
import finalProject.vote.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final TheoryRepository theoryRepository;
    private final VoteMapper voteMapper;

    public VoteResponseDto createOrUpdateVote(User currentUser, VoteCreateAndUpdateRequestDto dto) {
        UUID userId = getCurrentUserId(currentUser);
        Theory theory = theoryRepository.findById(dto.theoryId())
                .orElseThrow(() -> new NotFoundException("Theory not found with id: " + dto.theoryId()));

        Optional<Vote> existingVote = voteRepository.findByUser_IdAndTheory_Id(userId, dto.theoryId());

        Vote vote;

        if (existingVote.isPresent()) {
            vote = existingVote.get();
            vote.setType(dto.type());
        } else {
            vote = new Vote();
            vote.setUser(currentUser);
            vote.setTheory(theory);
            vote.setType(dto.type());
        }

        Vote savedVote = voteRepository.save(vote);

        return voteMapper.toDto(savedVote);
    }

    private UUID getCurrentUserId(User currentUser) {
        if (currentUser == null || currentUser.getId() == null) {
            throw new ForbiddenException("Authentication is required to vote");
        }

        return currentUser.getId();
    }

}
