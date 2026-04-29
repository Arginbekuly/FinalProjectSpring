package finalProject.contradiction.repository;

import finalProject.contradiction.entity.Contradiction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContradictionRepository extends JpaRepository<Contradiction, UUID> {
    Optional<Contradiction> findByTheoryIdAAndTheoryIdB(UUID theoryIdA, UUID theoryIdB);
    List<Contradiction> findAllByTheoryIdAOrTheoryIdB(UUID theoryIdA, UUID theoryIdB);
}
