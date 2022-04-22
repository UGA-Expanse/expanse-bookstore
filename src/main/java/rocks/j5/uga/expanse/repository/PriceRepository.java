package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocks.j5.uga.expanse.model.PriceDetail;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceRepository<findAll> extends JpaRepository<PriceDetail, Integer> {
    Optional<PriceDetail> findFirstByBook_Id(Integer id);

    Double getByBookId(Integer bookId);

    @Query("select p from PriceDetail p where p.book.id = ?1")
    Optional<BigDecimal> findByBookId(Integer id);


}
