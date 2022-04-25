package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getCartById(Long id);

    Cart findFirstByIdGreaterThanOrderByIdDesc(Long id);


}