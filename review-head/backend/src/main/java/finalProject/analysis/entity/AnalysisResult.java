package finalProject.analysis.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import finalProject.theory.entity.Theory;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analysis_results")
public class AnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "evidence_score", nullable = false)
    private Float evidenceScore;

    @Column(name = "consistency_score", nullable = false)
    private Float consistencyScore;

    @Column(name = "community_score", nullable = false)
    private Float communityScore;

    @Column(name = "final_credibility_score", nullable = false)
    private Float finalCredibilityScore;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "analyzed_at", nullable = false)
    private LocalDateTime analyzedAt;

    @Column(name = "theory_id", nullable = false)
    private UUID theoryId;

    @PrePersist
    public void prePersist() {
        this.analyzedAt = LocalDateTime.now();
    }



}
