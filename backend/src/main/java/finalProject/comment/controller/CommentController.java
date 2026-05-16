package finalProject.comment.controller;

import finalProject.comment.dto.request.CommentCreateRequestDto;
import finalProject.comment.dto.request.CommentUpdateRequestDto;
import finalProject.comment.dto.response.CommentResponseDto;
import finalProject.comment.service.CommentService;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody CommentCreateRequestDto dto
    ) {
        return commentService.createComment(currentUser, dto);
    }

    @GetMapping("/{commentId}")
    public CommentResponseDto getCommentById(@PathVariable UUID commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/theory/{theoryId}")
    public List<CommentResponseDto> getCommentsByTheoryId(@PathVariable UUID theoryId) {
        return commentService.getCommentsByTheoryId(theoryId);
    }

    @GetMapping("/user/{userId}")
    public List<CommentResponseDto> getCommentsByUserId(@PathVariable UUID userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto updateComment(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID commentId,
            @Valid @RequestBody CommentUpdateRequestDto dto
    ) {
        return commentService.updateComment(currentUser, commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID commentId
    ) {
        commentService.deleteComment(currentUser, commentId);
    }
}
