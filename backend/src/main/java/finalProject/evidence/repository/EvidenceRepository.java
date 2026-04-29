package finalProject.evidence.repository;

import finalProject.evidence.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EvidenceRepository extends JpaRepository<Evidence, UUID> {
    List<Evidence> findAllByUserId(UUID userId);
    List<Evidence> findAllByTheoryId(UUID theoryId);
}
