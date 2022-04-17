package rocks.j5.uga.expanse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Book;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * The Interface BookRepository.
 *
 * @author niknab
 * @version 1.0
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAll(Pageable pageable);
}
