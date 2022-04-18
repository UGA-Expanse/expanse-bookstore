package rocks.j5.uga.expanse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.Category;
import rocks.j5.uga.expanse.model.CategoryItem;

import java.util.List;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Integer> {
    List<CategoryItem> findAllByCategoryId(Integer categoryId);
}
