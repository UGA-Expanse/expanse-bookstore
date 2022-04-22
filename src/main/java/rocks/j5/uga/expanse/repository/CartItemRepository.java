package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import rocks.j5.uga.expanse.model.CartItem;

import java.util.List;
import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findByCart_Id(Long id);

    @Query("select c from CartItem c where c.cart.id = ?1")
    Set<CartItem> findByCart_IdEquals(Long id);

    @Query("select c from CartItem c where c.cart.id = ?1")
    List<CartItem> collectListByCartId(Long id);

}