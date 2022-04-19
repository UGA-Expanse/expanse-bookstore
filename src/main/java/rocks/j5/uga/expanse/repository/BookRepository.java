package rocks.j5.uga.expanse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocks.j5.uga.expanse.model.Author;
import rocks.j5.uga.expanse.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The Interface BookRepository.
 *
 * @author niknab
 * @version 1.0
 */
public interface BookRepository<findAll> extends JpaRepository<Book, Integer> {

    Page<Book> findAll(Pageable pageable);

    @Query("select b from Book b where upper(b.title) like upper(?1) or upper(b.publisher.publisherName) like upper(?2)")
    Page<Book> findAllByIdAndTitle(String title, String publisherName, Pageable pageable);

    @Query("select b from Book b " +
            "where upper(b.title) like upper(concat('%', ?1, '%')) or upper(b.publisher.publisherName) like upper(concat('%', ?2, '%'))")
    List<Book> findAllWithContains(String title, String publisherName, Pageable pageable);

    List<Author> findByTitleContainsIgnoreCase(String title);







//    Page<Book> findAllByCategoryItem(Category category);
}
