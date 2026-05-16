package finalProject.vote.controller;

import finalProject.user.entity.User;
import finalProject.vote.dto.request.VoteCreateAndUpdateRequestDto;
import finalProject.vote.dto.response.VoteResponseDto;
import finalProject.vote.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponseDto createOrUpdateVote(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody VoteCreateAndUpdateRequestDto dto
    ) {
        return voteService.createOrUpdateVote(currentUser, dto);
    }
}
