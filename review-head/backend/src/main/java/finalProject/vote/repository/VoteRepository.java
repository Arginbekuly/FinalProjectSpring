package finalProject.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.vote.entity.Vote;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
}
