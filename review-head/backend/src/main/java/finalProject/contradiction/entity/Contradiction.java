package finalProject.contradiction.entity;
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
@Table(
        name = "contradictions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"theory_id_a", "theory_id_b"})
        }
)
public class Contradiction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "severity", nullable = false)
    private Integer severity;

    @Enumerated(EnumType.STRING)
    private ContradictionStatus status;

    @Column(name = "theory_id_a", nullable = false)
    private UUID theoryIdA;

    @Column(name = "theory_id_b", nullable = false)
    private UUID theoryIdB;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
