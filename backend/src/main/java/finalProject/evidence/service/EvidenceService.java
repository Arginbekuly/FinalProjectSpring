package finalProject.evidence.service;


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
import finalProject.user.client.UserClient;
import finalProject.user.entity.User;
import finalProject.user.entity.UserRole;
import finalProject.user.repository.UserRepository;
import finalProject.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static finalProject.evidence.entity.EvidenceType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class EvidenceService {
    private final EvidenceRepository evidenceRepository;
    private final EvidenceMapper evidenceMapper;
    private final TheoryRepository theoryRepository;

    public EvidenceResponseDto createEvidence(User currentUser,EvidenceCreateRequestDto evidenceDto) {
        Evidence evidence = evidenceMapper.toEvidence(evidenceDto);
        evidence.setUserId(currentUser.getId());

        Evidence savedEvidence = evidenceRepository.save(evidence);
        return evidenceMapper.toDto(savedEvidence);
    }

    public EvidenceResponseDto getEvidenceById(UUID evidenceId) {
        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() -> new NotFoundException("Evidence with id: " + evidenceId + " not found"));
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


    public List<EvidenceResponseDto> getAllEvidences(){
        return evidenceRepository.findAll().stream()
                .map(evidenceMapper::toDto)
                .toList();
    }

    public List<EvidenceResponseDto> getEvidenceByUserId(UUID userId) {
        return evidenceRepository.findByUserId(userId).stream()
                .map(evidenceMapper::toDto)
                .toList();
    }

    public List<EvidenceResponseDto> getEvidenceByTheoryId(UUID theoryId) {
        ensureTheoryExists(theoryId);

        return evidenceRepository.findByTheoryId(theoryId).stream()
                .map(evidenceMapper::toDto)
                .toList();
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
                    throw new IllegalArgumentException("TEXT evidence must contain content");
                }
            }
            case LINK, IMAGE_REFERENCE, VIDEO_REFERENCE, DOCUMENT_REFERENCE -> {
                if (sourceUrl == null || sourceUrl.isBlank()) {
                    throw new IllegalArgumentException(type + " evidence must contain sourceUrl");
                }
            }
        }
    }
}
