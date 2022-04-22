package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import rocks.j5.uga.expanse.model.CategoryItem;

import java.util.List;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Integer> {
//    List<CategoryItem> findAllByCategoryId(Integer categoryId);

//    List<CategoryItem> findAllByCategoryId(@NonNull Integer category_id);

//    List<CategoryItem> findAllByCategoryId2(Integer category_id);

    List<CategoryItem> findAllByCategory(Integer category_id);



//    List<CategoryItem> findCategoryItemFromCatId(Integer category_id);





}
