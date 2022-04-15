package rocks.j5.uga.expanse.repository;

import rocks.j5.uga.expanse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface UserRepository.
 *
 * @author niknab
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, String> {

}
