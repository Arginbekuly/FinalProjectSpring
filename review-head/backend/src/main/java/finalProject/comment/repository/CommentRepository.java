package finalProject.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.comment.entity.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
