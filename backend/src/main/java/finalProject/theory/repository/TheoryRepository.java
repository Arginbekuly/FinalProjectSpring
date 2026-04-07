package finalProject.theory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.theory.entity.Theory;

import java.util.UUID;

public interface TheoryRepository extends JpaRepository<Theory, UUID> {
}
