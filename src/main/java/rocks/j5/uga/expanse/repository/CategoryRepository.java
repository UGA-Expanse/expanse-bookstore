package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Category;

/**
 * The Interface CategoryRepository.
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
