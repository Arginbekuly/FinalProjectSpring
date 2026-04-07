package finalProject.contradiction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.contradiction.entity.Contradiction;

import java.util.UUID;

public interface ContradictionRepository extends JpaRepository<Contradiction, UUID> {
}
