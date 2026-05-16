package finalProject.evidence.controller;

import finalProject.evidence.dto.request.EvidenceCreateRequestDto;
import finalProject.evidence.dto.request.EvidenceUpdateRequestDto;
import finalProject.evidence.dto.response.EvidenceResponseDto;
import finalProject.evidence.service.EvidenceService;
import finalProject.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/evidences")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class EvidenceController {

    private final EvidenceService evidenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EvidenceResponseDto createEvidence(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody EvidenceCreateRequestDto dto
    ) {
        return evidenceService.createEvidence(currentUser, dto);
    }

    @GetMapping("/{evidenceId}")
    public EvidenceResponseDto getEvidenceById(@PathVariable UUID evidenceId) {
        return evidenceService.getEvidenceById(evidenceId);
    }

    @GetMapping("/getAllEvidences")
    public List<EvidenceResponseDto> getAllEvidences() {
        return evidenceService.getAllEvidences();
    }

    @GetMapping("/user/{userId}")
    public List<EvidenceResponseDto> getEvidenceByUserId(@PathVariable UUID userId) {
        return evidenceService.getEvidenceByUserId(userId);
    }

    @GetMapping("/theory/{theoryId}")
    public List<EvidenceResponseDto> getEvidenceByTheoryId(@PathVariable UUID theoryId) {
        return evidenceService.getEvidenceByTheoryId(theoryId);
    }

    @PatchMapping("/{evidenceId}")
    public EvidenceResponseDto updateEvidence(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID evidenceId,
            @Valid @RequestBody EvidenceUpdateRequestDto request
    ) {
        return evidenceService.updateEvidence(currentUser, evidenceId, request);
    }

    @DeleteMapping("/{evidenceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvidence(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID evidenceId
    ) {
        evidenceService.deleteEvidence(currentUser, evidenceId);
    }
}
