package rocks.j5.uga.expanse.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.BookAuthor;
import rocks.j5.uga.expanse.model.BookAuthorId;

import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    @Query("select b from BookAuthor b " +
            "where upper(b.author.authorName) like upper(concat('%', ?1, '%')) or upper(b.book.title) like upper(concat('%', ?2, '%'))")
    List<BookAuthor> findAllQuery(String authorName, String title, Pageable pageable);

    @Query("select b from BookAuthor b " +
            "where upper(b.book.title) like upper(concat('%', ?1, '%')) or upper(b.author.authorName) like upper(concat('%', ?2, '%')) or upper(b.book.publisher.publisherName) like upper(concat('%', ?3, '%'))")
    List<Book> findByBook_TitleContainsIgnoreCaseOrAuthor_AuthorNameContainsIgnoreCaseOrBook_Publisher_PublisherNameContainsIgnoreCase(String title, String authorName, String publisherName, Pageable pageable);

    List<Book> findByAuthor_AuthorNameContainsIgnoreCase(String authorName);



}