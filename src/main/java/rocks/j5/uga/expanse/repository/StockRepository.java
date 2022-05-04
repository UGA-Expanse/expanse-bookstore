package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.Stock;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
}
