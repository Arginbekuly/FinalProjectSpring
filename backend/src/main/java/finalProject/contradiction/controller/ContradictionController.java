package finalProject.contradiction.controller;

import finalProject.contradiction.dto.request.ContradictionCreateRequestDto;
import finalProject.contradiction.dto.response.ContradictionResponseDto;
import finalProject.contradiction.service.ContradictionService;
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
@RequestMapping("/api/v1/contradictions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ContradictionController {

    private final ContradictionService contradictionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContradictionResponseDto createContradiction(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody ContradictionCreateRequestDto dto
    ) {
        return contradictionService.createContradiction(currentUser, dto);
    }

    @GetMapping("/{contradictionId}")
    public ContradictionResponseDto getContradictionById(@PathVariable UUID contradictionId) {
        return contradictionService.getContradictionById(contradictionId);
    }

    @GetMapping("/getAllContradictions")
    public List<ContradictionResponseDto> getAllContradictions() {
        return contradictionService.getAllContradictions();
    }

    @GetMapping("/theory/{theoryId}")
    public List<ContradictionResponseDto> getContradictionsByTheoryId(@PathVariable UUID theoryId) {
        return contradictionService.getContradictionsByTheoryId(theoryId);
    }

    @PatchMapping("/{contradictionId}/status")
    public ContradictionResponseDto changeStatus(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID contradictionId
    ) {
        return contradictionService.changeStatus(currentUser, contradictionId);
    }

    @DeleteMapping("/{contradictionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContradiction(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID contradictionId
    ) {
        contradictionService.deleteContradiction(currentUser, contradictionId);
    }
}
