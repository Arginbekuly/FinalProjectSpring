package finalProject.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.tag.entity.Tag;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
