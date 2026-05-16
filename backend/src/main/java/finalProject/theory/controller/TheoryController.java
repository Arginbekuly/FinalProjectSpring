package finalProject.theory.controller;

import finalProject.theory.dto.request.TheoryCreateRequestDto;
import finalProject.theory.dto.request.TheoryUpdateRequestDto;
import finalProject.theory.dto.response.TheoryResponseDto;
import finalProject.theory.service.TheoryService;
import finalProject.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/theories")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TheoryController {

    private final TheoryService theoryService;

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TheoryResponseDto createTheory(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID userId,
            @Valid @RequestBody TheoryCreateRequestDto dto
    ) {
        return theoryService.createTheory(currentUser, userId, dto);
    }

    @GetMapping("/{theoryId}")
    public TheoryResponseDto getTheoryById(@PathVariable UUID theoryId) {
        return theoryService.getTheoryById(theoryId);
    }

    @GetMapping("/getAllTheories")
    public List<TheoryResponseDto> getAllTheories() {
        return theoryService.getAllTheories();
    }

    @GetMapping("/user/{userId}")
    public List<TheoryResponseDto> getTheoriesByUserId(@PathVariable UUID userId) {
        return theoryService.getTheoriesByUserId(userId);
    }

    @PutMapping("/{theoryId}")
    public TheoryResponseDto updateTheory(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID theoryId,
            @Valid @RequestBody TheoryUpdateRequestDto request
    ) {
        return theoryService.updateTheory(currentUser, theoryId, request);
    }

    @PatchMapping("/{theoryId}/status")
    public TheoryResponseDto changeStatus(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID theoryId
    ) {
        return theoryService.changeStatus(currentUser, theoryId);
    }

    @DeleteMapping("/{theoryId}")
    public TheoryResponseDto softDeleteTheory(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID theoryId
    ) {
        return theoryService.softDeleteTheory(currentUser, theoryId);
    }
}
