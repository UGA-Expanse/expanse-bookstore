package rocks.j5.uga.expanse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import rocks.j5.uga.expanse.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}