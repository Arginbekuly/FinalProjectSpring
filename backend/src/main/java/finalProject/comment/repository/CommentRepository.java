package finalProject.comment.repository;

import finalProject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByTheoryId(UUID theoryId);
    List<Comment> findAllByUserId(UUID userId);
}
