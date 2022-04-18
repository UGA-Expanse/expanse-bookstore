package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.Category;
import rocks.j5.uga.expanse.model.CategoryItem;
import rocks.j5.uga.expanse.repository.BookRepository;
import rocks.j5.uga.expanse.repository.CategoryItemRepository;
import rocks.j5.uga.expanse.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CatalogService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    public CatalogService(BookRepository bookRepository, CategoryRepository categoryRepository, CategoryItemRepository categoryItemRepository) {

        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.categoryItemRepository = categoryItemRepository;
    }

    public List<Book> findAll() {

        Page<Book> pageOfBooks = bookRepository.findAll(PageRequest.ofSize(100));
        List<Book> books = pageOfBooks.get().collect(Collectors.toList());
        return books;
    }

    public List<Book> findAllByCategory(String cas) {

        Category category = categoryRepository.findCategoryByName(cas);
        List<CategoryItem> categoryItems = categoryItemRepository.findAllByCategoryId(category.getId());
        List<Book> books = categoryItems.stream().map(CategoryItem::getBook).collect(Collectors.toList());
        return books;
    }
}
