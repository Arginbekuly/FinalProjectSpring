package finalProject.evidence.service;

import finalProject.common.exception.BadRequestException;
import finalProject.common.exception.ForbiddenException;
import finalProject.common.exception.NotFoundException;
import finalProject.evidence.dto.request.EvidenceCreateRequestDto;
import finalProject.evidence.dto.request.EvidenceUpdateRequestDto;
import finalProject.evidence.dto.response.EvidenceResponseDto;
import finalProject.evidence.entity.Evidence;
import finalProject.evidence.entity.EvidenceType;
import finalProject.evidence.mapper.EvidenceMapper;
import finalProject.evidence.repository.EvidenceRepository;
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
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final EvidenceMapper evidenceMapper;
    private final TheoryRepository theoryRepository;

    public EvidenceResponseDto createEvidence(User currentUser, EvidenceCreateRequestDto evidenceDto) {
        ensureTheoryExists(evidenceDto.theoryId());
        validateEvidenceByType(evidenceDto.type(), evidenceDto.content(), evidenceDto.sourceUrl());

        Evidence evidence = evidenceMapper.toEvidence(evidenceDto);
        evidence.setUserId(getCurrentUserId(currentUser));

        Evidence savedEvidence = evidenceRepository.save(evidence);
        return evidenceMapper.toDto(savedEvidence);
    }

    public EvidenceResponseDto getEvidenceById(UUID evidenceId) {
        Evidence evidence = getEvidenceOrThrow(evidenceId);
        return evidenceMapper.toDto(evidence);
    }

    public EvidenceResponseDto updateEvidence(User currentUser, UUID evidenceId, EvidenceUpdateRequestDto request) {
        Evidence evidence = getEvidenceOrThrow(evidenceId);
        validateUserCanManageEvidence(currentUser, evidence);
        validateEvidenceByType(
                request.type() != null ? request.type() : evidence.getType(),
                request.content() != null ? request.content() : evidence.getContent(),
                request.sourceUrl() != null ? request.sourceUrl() : evidence.getSourceUrl()
        );

        evidenceMapper.updateEvidenceFromDto(request, evidence);

        Evidence savedEvidence = evidenceRepository.save(evidence);
        return evidenceMapper.toDto(savedEvidence);
    }

    public List<EvidenceResponseDto> getAllEvidences() {
        return evidenceMapper.toDtoList(evidenceRepository.findAll());
    }

    public List<EvidenceResponseDto> getEvidenceByUserId(UUID userId) {
        return evidenceMapper.toDtoList(evidenceRepository.findAllByUserId(userId));
    }

    public List<EvidenceResponseDto> getEvidenceByTheoryId(UUID theoryId) {
        ensureTheoryExists(theoryId);
        return evidenceMapper.toDtoList(evidenceRepository.findAllByTheoryId(theoryId));
    }

    public void deleteEvidence(User currentUser, UUID evidenceId) {
        Evidence evidence = getEvidenceOrThrow(evidenceId);
        validateUserCanManageEvidence(currentUser, evidence);
        evidenceRepository.delete(evidence);
    }

    private Evidence getEvidenceOrThrow(UUID evidenceId) {
        return evidenceRepository.findById(evidenceId)
                .orElseThrow(() -> new NotFoundException("Evidence with id: " + evidenceId + " not found"));
    }

    private void ensureTheoryExists(UUID theoryId) {
        if (!theoryRepository.existsById(theoryId)) {
            throw new NotFoundException("Theory with id: " + theoryId + " not found");
        }
    }

    private void validateUserCanManageEvidence(User currentUser, Evidence evidence) {
        if (currentUser != null && currentUser.getRole() == UserRole.ADMIN) {
            return;
        }

        if (currentUser == null || currentUser.getId() == null || !currentUser.getId().equals(evidence.getUserId())) {
            throw new ForbiddenException("You can manage only your own evidence");
        }
    }

    private void validateEvidenceByType(EvidenceType type, String content, String sourceUrl) {
        switch (type) {
            case TEXT -> {
                if (content == null || content.isBlank()) {
                    throw new BadRequestException("TEXT evidence must contain content");
                }
            }
            case LINK, IMAGE_REFERENCE, VIDEO_REFERENCE, DOCUMENT_REFERENCE -> {
                if (sourceUrl == null || sourceUrl.isBlank()) {
                    throw new BadRequestException(type + " evidence must contain sourceUrl");
                }
            }
        }
    }

    private UUID getCurrentUserId(User currentUser) {
        if (currentUser == null || currentUser.getId() == null) {
            throw new ForbiddenException("Authentication is required to manage evidence");
        }

        return currentUser.getId();
    }
}
