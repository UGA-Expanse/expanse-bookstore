package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.Category;
import rocks.j5.uga.expanse.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Book> service(String category) {
        return null;
    }

    public List<Book> findAllByCategory(String category) {
        return null;
    }

    public void save(Category category) {
    }

    public List<Category> findAll() {
        return null;
    }

    public Category findAllBooksByCategory(String category) {
        Category foundCategory = categoryRepository.findCategoryByName(category);
        return foundCategory;
    }

    public void deleteById(int id) {
    }

    public boolean existsById(int id) {
        return false;
    }
}
