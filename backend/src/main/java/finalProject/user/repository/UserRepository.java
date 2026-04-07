package finalProject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finalProject.user.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
