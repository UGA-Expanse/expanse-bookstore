package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Category;

import java.util.Optional;

/**
 * The Interface CategoryRepository.
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByName(String name);

    Optional<Category> findByIdIs(Integer id);

    Optional<Category> findByNameIsIgnoreCase(String name);





}
