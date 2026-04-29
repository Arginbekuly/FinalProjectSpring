package finalProject.comment.service;

import finalProject.comment.dto.request.CommentCreateRequestDto;
import finalProject.comment.dto.request.CommentUpdateRequestDto;
import finalProject.comment.dto.response.CommentResponseDto;
import finalProject.comment.entity.Comment;
import finalProject.comment.mapper.CommentMapper;
import finalProject.comment.repository.CommentRepository;
import finalProject.common.exception.ForbiddenException;
import finalProject.common.exception.NotFoundException;
import finalProject.theory.repository.TheoryRepository;
import finalProject.user.entity.User;
import finalProject.user.entity.UserRole;
import finalProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final TheoryRepository theoryRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    public CommentResponseDto createComment(User currentUser, CommentCreateRequestDto dto) {
        ensureTheoryExists(dto.theoryId());

        Comment comment = commentMapper.toComment(dto);
        comment.setUserId(getCurrentUserId(currentUser));

        Comment createdComment = commentRepository.save(comment);
        return commentMapper.toDto(createdComment);
    }

    public CommentResponseDto getCommentById(UUID commentId) {
        Comment comment = getCommentOrThrow(commentId);
        return commentMapper.toDto(comment);
    }

    public List<CommentResponseDto> getCommentsByTheoryId(UUID theoryId) {
        ensureTheoryExists(theoryId);
        return commentMapper.toDtoList(commentRepository.findAllByTheoryId(theoryId));
    }

    public List<CommentResponseDto> getCommentsByUserId(UUID userId) {
        ensureUserExists(userId);
        return commentMapper.toDtoList(commentRepository.findAllByUserId(userId));
    }

    public CommentResponseDto updateComment(User currentUser, UUID commentId, CommentUpdateRequestDto dto) {
        Comment comment = getCommentOrThrow(commentId);
        validateUserCanManageComment(currentUser, comment);

        commentMapper.updateCommentFromDto(dto, comment);

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    public void deleteComment(User currentUser, UUID commentId) {
        Comment comment = getCommentOrThrow(commentId);
        validateUserCanManageComment(currentUser, comment);
        commentRepository.delete(comment);
    }

    private Comment getCommentOrThrow(UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with id: " + commentId));
    }

    private void ensureTheoryExists(UUID theoryId) {
        if (!theoryRepository.existsById(theoryId)) {
            throw new NotFoundException("Theory not found with id: " + theoryId);
        }
    }

    private void ensureUserExists(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found with id: " + userId);
        }
    }

    private void validateUserCanManageComment(User currentUser, Comment comment) {
        if (currentUser != null && currentUser.getRole() == UserRole.ADMIN) {
            return;
        }

        if (currentUser == null || comment.getUserId() == null || !currentUser.getId().equals(comment.getUserId())) {
            throw new ForbiddenException("You do not have permission to manage this comment");
        }
    }

    private UUID getCurrentUserId(User currentUser) {
        if (currentUser == null || currentUser.getId() == null) {
            throw new ForbiddenException("Authentication is required to comment");
        }

        return currentUser.getId();
    }
}
