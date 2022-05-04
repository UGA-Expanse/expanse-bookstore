package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>
{
    Cart getCartById(Long id);

    Cart findFirstByIdGreaterThanOrderByIdDesc(Long id);

    Optional<Cart> findByCustomerUserid(String userid);

    Optional<Cart> findByCartSessionId(String cartSessionId);






}