package finalProject.vote.repository;

import finalProject.vote.entity.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.vote.entity.Vote;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
    Optional<Vote> findByUser_IdAndTheory_Id(UUID userId, UUID theoryId);
    List<Vote> findAllByTheory_Id(UUID theoryId);
    long countByTheory_IdAndType(UUID theoryId, VoteType type);
    void deleteByUser_IdAndTheory_Id(UUID userId, UUID theoryId);
}
