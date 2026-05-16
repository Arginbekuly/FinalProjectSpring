package finalProject.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.analysis.entity.AnalysisResult;

import java.util.UUID;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, UUID> {
}
