package finalProject.evidence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.evidence.entity.Evidence;

import java.util.UUID;

public interface EvidenceRepository extends JpaRepository<Evidence, UUID> {
}
