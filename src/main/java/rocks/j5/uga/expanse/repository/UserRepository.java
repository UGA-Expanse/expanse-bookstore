package rocks.j5.uga.expanse.repository;

import rocks.j5.uga.expanse.model.UserO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The Interface UserRepository.
 *
 * @author niknab
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<UserO, String> {

    @Override
    Optional<UserO> findById(String username);

    Optional<UserO> findByEmail(String email);





}
