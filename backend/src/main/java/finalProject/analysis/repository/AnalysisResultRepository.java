package finalProject.analysis.repository;

import finalProject.analysis.entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, UUID> {
    Optional<AnalysisResult> findByTheoryId(UUID theoryId);
}
