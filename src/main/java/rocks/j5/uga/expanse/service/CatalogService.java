package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CatalogService {

    private final BookRepository bookRepository;

    public CatalogService(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        Page<Book> pageOfBooks = bookRepository.findAll(PageRequest.ofSize(100));
        List<Book> books = pageOfBooks.get().collect(Collectors.toList());
        return books;
    }
}
