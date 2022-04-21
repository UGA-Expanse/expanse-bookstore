package rocks.j5.uga.expanse;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}