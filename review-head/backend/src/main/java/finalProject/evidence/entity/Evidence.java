package finalProject.evidence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import finalProject.theory.entity.Theory;
import finalProject.user.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evidence")
public class Evidence{
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EvidenceType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "source_url", nullable = false)
    private String sourceUrl;

    @Column(name = "source_name", nullable = false)
    private String sourceName;

    @Column(name = "reliability_score", nullable = false)
    private Integer reliabilityScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",  nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    @Column(name = "theory_id", nullable = false)
    private UUID theoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theory_id", nullable = false, insertable = false, updatable = false)
    private Theory theory;

    @Column(name = "user_id", nullable = false)
    private UUID userId;


}
