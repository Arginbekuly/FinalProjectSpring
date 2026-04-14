package finalProject.theory.service;

import feign.FeignException;
import finalProject.common.exception.ForbiddenException;
import finalProject.common.exception.NotFoundException;
import finalProject.theory.dto.request.TheoryCreateRequestDto;
import finalProject.theory.dto.request.TheoryUpdateRequestDto;
import finalProject.theory.dto.response.TheoryResponseDto;
import finalProject.theory.entity.Theory;
import finalProject.theory.entity.TheoryStatus;
import finalProject.theory.mapper.TheoryMapper;
import finalProject.theory.repository.TheoryRepository;
import finalProject.user.client.UserClient;
import finalProject.user.dto.response.UserResponseDto;
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
public class TheoryService {

    private final TheoryRepository theoryRepository;
    private final TheoryMapper theoryMapper;
    private final UserClient userClient;

    public TheoryResponseDto createTheory(User currentUser, UUID userId, TheoryCreateRequestDto theoryDto) {
        ensureUserExists(userId);
        validateUserCanManageUserTheories(currentUser, userId);

        Theory theory = theoryMapper.toTheory(theoryDto);
        theory.setUserId(userId);

        Theory savedTheory = theoryRepository.save(theory);
        return theoryMapper.toDto(savedTheory);
    }

    public TheoryResponseDto getTheoryById(UUID theoryId) {
        Theory theory = theoryRepository.findById(theoryId)
                .orElseThrow(() -> new NotFoundException("Theory not found with id: " + theoryId));
        return theoryMapper.toDto(theory);
    }

    public List<TheoryResponseDto> getAllTheories() {
        return theoryRepository.findAll().stream()
                .map(theoryMapper::toDto)
                .toList();
    }

    public List<TheoryResponseDto> getTheoriesByUserId(UUID userId) {
        ensureUserExists(userId);

        return theoryRepository.findByUserId(userId).stream()
                .map(theoryMapper::toDto)
                .toList();
    }

    public TheoryResponseDto updateTheory(User currentUser, UUID theoryId, TheoryUpdateRequestDto request) {
        Theory theory = theoryRepository.findById(theoryId)
                .orElseThrow(() -> new NotFoundException("Theory not found with id: " + theoryId));
        validateUserCanManageTheory(currentUser, theory);

        theoryMapper.updateTheoryFromDto(request, theory);

        Theory savedTheory = theoryRepository.save(theory);
        return theoryMapper.toDto(savedTheory);
    }

    public TheoryResponseDto changeStatus(User currentUser, UUID theoryId) {
        Theory theory = theoryRepository.findById(theoryId)
                .orElseThrow(() -> new NotFoundException("Theory not found with id: " + theoryId));
        validateUserCanManageTheory(currentUser, theory);

        if (theory.getStatus() == TheoryStatus.DRAFT) {
            theory.setStatus(TheoryStatus.PENDING_REVIEW);
        } else if (theory.getStatus() == TheoryStatus.PENDING_REVIEW) {
            theory.setStatus(TheoryStatus.PUBLISHED);
        } else if (theory.getStatus() == TheoryStatus.PUBLISHED) {
            theory.setStatus(TheoryStatus.FLAGGED);
        } else if (theory.getStatus() == TheoryStatus.FLAGGED) {
            theory.setStatus(TheoryStatus.PUBLISHED);
        } else if (theory.getStatus() == TheoryStatus.REJECTED) {
            theory.setStatus(TheoryStatus.PENDING_REVIEW);
        }

        Theory savedTheory = theoryRepository.save(theory);
        return theoryMapper.toDto(savedTheory);
    }

    public TheoryResponseDto softDeleteTheory(User currentUser, UUID theoryId) {
        Theory theory = theoryRepository.findById(theoryId)
                .orElseThrow(() -> new NotFoundException("Theory not found with id: " + theoryId));
        validateUserCanManageTheory(currentUser, theory);

        theory.setStatus(TheoryStatus.ARCHIVE);

        Theory savedTheory = theoryRepository.save(theory);
        return theoryMapper.toDto(savedTheory);
    }

    private void ensureUserExists(UUID userId) {
        try {
            UserResponseDto user = userClient.getUserById(userId);
            if (user == null) {
                throw new NotFoundException("User not found with id: " + userId);
            }
        } catch (FeignException exception) {
            if (exception.status() == 404) {
                throw new NotFoundException("User not found with id: " + userId);
            }
            throw exception;
        }
    }

    private void validateUserCanManageTheory(User currentUser, Theory theory) {
        validateUserCanManageUserTheories(currentUser, theory.getUserId());
    }

    private void validateUserCanManageUserTheories(User currentUser, UUID userId) {
        if (isAdmin(currentUser)) {
            return;
        }

        if (currentUser == null || currentUser.getId() == null || !currentUser.getId().equals(userId)) {
            throw new ForbiddenException("You can manage only your own theories");
        }
    }

    private boolean isAdmin(User currentUser) {
        return currentUser != null && currentUser.getRole() == UserRole.ADMIN;
    }
}
