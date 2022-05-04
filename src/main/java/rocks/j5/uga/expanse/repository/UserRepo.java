package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
