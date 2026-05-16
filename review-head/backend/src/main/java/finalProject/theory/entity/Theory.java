package finalProject.theory.entity;

import jakarta.persistence.*;
import lombok.*;
import finalProject.evidence.entity.Evidence;
import finalProject.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "theory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evidence> evidence = new ArrayList<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TheoryStatus status;

    @Column(name = "credibility_score", nullable = false)
    private Float credibilityScore;

    @Column(name = "popularity_score", nullable = false)
    private Float popularityScore;

    @Column(name = "contradiction_count", nullable = false)
    private Integer contradictionCount;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @PrePersist
    public void prePersist()
    {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        publishedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate()
    {
        updatedAt = LocalDateTime.now();
    }

}
