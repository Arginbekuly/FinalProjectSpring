package finalProject.contradiction.service;

import finalProject.common.exception.BadRequestException;
import finalProject.common.exception.ForbiddenException;
import finalProject.common.exception.NotFoundException;
import finalProject.contradiction.dto.request.ContradictionCreateRequestDto;
import finalProject.contradiction.dto.response.ContradictionResponseDto;
import finalProject.contradiction.entity.Contradiction;
import finalProject.contradiction.entity.ContradictionStatus;
import finalProject.contradiction.mapper.ContradictionMapper;
import finalProject.contradiction.repository.ContradictionRepository;
import finalProject.theory.entity.Theory;
import finalProject.theory.repository.TheoryRepository;
import finalProject.user.entity.User;
import finalProject.user.entity.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContradictionService {

    private final ContradictionRepository contradictionRepository;
    private final ContradictionMapper contradictionMapper;
    private final TheoryRepository theoryRepository;

    public ContradictionResponseDto createContradiction(User currentUser, ContradictionCreateRequestDto dto) {
        UUID userId = getCurrentUserId(currentUser);
        Theory theoryA = getTheoryOrThrow(dto.theoryId());
        Theory theoryB = getTheoryOrThrow(dto.contradictingTheoryId());

        validateDifferentTheories(theoryA.getId(), theoryB.getId());
        ensureNoDuplicateContradiction(theoryA.getId(), theoryB.getId());

        Contradiction contradiction = contradictionMapper.toEntity(dto);
        contradiction.setUserId(userId);
        contradiction.setStatus(ContradictionStatus.DETECTED);

        Contradiction savedContradiction = contradictionRepository.save(contradiction);
        refreshContradictionCounts(theoryA.getId(), theoryB.getId());
        return toDto(savedContradiction);
    }

    public ContradictionResponseDto getContradictionById(UUID contradictionId) {
        return toDto(getContradictionOrThrow(contradictionId));
    }

    public List<ContradictionResponseDto> getAllContradictions() {
        return contradictionRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public List<ContradictionResponseDto> getContradictionsByTheoryId(UUID theoryId) {
        getTheoryOrThrow(theoryId);
        return contradictionRepository.findAllByTheoryIdAOrTheoryIdB(theoryId, theoryId).stream()
                .map(this::toDto)
                .toList();
    }

    public ContradictionResponseDto changeStatus(User currentUser, UUID contradictionId) {
        Contradiction contradiction = getContradictionOrThrow(contradictionId);
        validateUserCanManageContradiction(currentUser, contradiction);

        contradiction.setStatus(nextStatus(contradiction.getStatus()));

        Contradiction savedContradiction = contradictionRepository.save(contradiction);
        refreshContradictionCounts(savedContradiction.getTheoryIdA(), savedContradiction.getTheoryIdB());
        return toDto(savedContradiction);
    }

    public void deleteContradiction(User currentUser, UUID contradictionId) {
        Contradiction contradiction = getContradictionOrThrow(contradictionId);
        validateUserCanManageContradiction(currentUser, contradiction);

        contradictionRepository.delete(contradiction);
        refreshContradictionCounts(contradiction.getTheoryIdA(), contradiction.getTheoryIdB());
    }

    private Contradiction getContradictionOrThrow(UUID contradictionId) {
        return contradictionRepository.findById(contradictionId)
                .orElseThrow(() -> new NotFoundException("Contradiction not found with id: " + contradictionId));
    }

    private Theory getTheoryOrThrow(UUID theoryId) {
        return theoryRepository.findById(theoryId)
                .orElseThrow(() -> new NotFoundException("Theory not found with id: " + theoryId));
    }

    private void validateDifferentTheories(UUID theoryIdA, UUID theoryIdB) {
        if (theoryIdA.equals(theoryIdB)) {
            throw new BadRequestException("Theory cannot contradict itself");
        }
    }

    private void ensureNoDuplicateContradiction(UUID theoryIdA, UUID theoryIdB) {
        boolean exists = contradictionRepository.findByTheoryIdAAndTheoryIdB(theoryIdA, theoryIdB).isPresent()
                || contradictionRepository.findByTheoryIdAAndTheoryIdB(theoryIdB, theoryIdA).isPresent();

        if (exists) {
            throw new BadRequestException("Contradiction between these theories already exists");
        }
    }

    private void validateUserCanManageContradiction(User currentUser, Contradiction contradiction) {
        if (currentUser != null && currentUser.getRole() == UserRole.ADMIN) {
            return;
        }

        if (currentUser == null || currentUser.getId() == null || !currentUser.getId().equals(contradiction.getUserId())) {
            throw new ForbiddenException("You can manage only your own contradictions");
        }
    }

    private UUID getCurrentUserId(User currentUser) {
        if (currentUser == null || currentUser.getId() == null) {
            throw new ForbiddenException("Authentication is required to manage contradictions");
        }

        return currentUser.getId();
    }

    private ContradictionStatus nextStatus(ContradictionStatus currentStatus) {
        return switch (currentStatus) {
            case DETECTED -> ContradictionStatus.CONFIRMED;
            case CONFIRMED -> ContradictionStatus.REJECTED;
            case REJECTED -> ContradictionStatus.DETECTED;
        };
    }

    private void refreshContradictionCounts(UUID theoryIdA, UUID theoryIdB) {
        updateContradictionCount(getTheoryOrThrow(theoryIdA));
        updateContradictionCount(getTheoryOrThrow(theoryIdB));
    }

    private void updateContradictionCount(Theory theory) {
        int activeCount = (int) contradictionRepository.findAllByTheoryIdAOrTheoryIdB(theory.getId(), theory.getId()).stream()
                .filter(contradiction -> contradiction.getStatus() != ContradictionStatus.REJECTED)
                .count();
        theory.setContradictionCount(activeCount);
        theoryRepository.save(theory);
    }

    private ContradictionResponseDto toDto(Contradiction contradiction) {
        ContradictionResponseDto baseDto = contradictionMapper.toDto(contradiction);
        String theoryTitle = getTheoryOrThrow(baseDto.theoryId()).getTitle();
        String contradictingTheoryTitle = getTheoryOrThrow(baseDto.contradictingTheoryId()).getTitle();

        return new ContradictionResponseDto(
                baseDto.id(),
                baseDto.theoryId(),
                theoryTitle,
                baseDto.contradictingTheoryId(),
                contradictingTheoryTitle,
                baseDto.reason(),
                baseDto.createdAt()
        );
    }
}
