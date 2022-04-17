package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.User;

import java.util.Optional;

/**
 * The Interface BookRepository.
 *
 * @author niknab
 * @version 1.0
 */
public interface BookRepository extends JpaRepository<User, String> {

    @Override
    Optional<User> findById(String username);

    Optional<User> findAllById();



}
