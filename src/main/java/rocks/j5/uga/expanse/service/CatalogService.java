package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.BookAuthorRepository;
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

    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    public CatalogService(BookAuthorRepository bookAuthorRepository, BookRepository bookRepository, CategoryRepository categoryRepository, CategoryItemRepository categoryItemRepository) {
        this.bookAuthorRepository = bookAuthorRepository;

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
        List<Book> books = categoryItems.stream().map(CategoryItem::getBook).limit(100l).collect(Collectors.toList());
        return books;
    }

    public List<Book> findAllBySearch(String term) {
        Page<Book> byTitle = bookRepository.findAllByIdAndTitle(term, term, PageRequest.ofSize(100));

        List<Book> books = byTitle.stream().collect(Collectors.toList());
        return books;
    }

    public List<Book> findAllUsingContains(String term) {
        List<Book> books = bookRepository.findAllWithContains(term, term, PageRequest.ofSize(100));

        return books;
    }
}
