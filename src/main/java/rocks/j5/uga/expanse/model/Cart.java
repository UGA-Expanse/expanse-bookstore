package rocks.j5.uga.expanse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "cart")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cart_id", nullable = false)
    private Long id;

    @Column(name = "cart_session_id", length = 200)
    private String cartSessionId;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cart", fetch = FetchType.LAZY)
    private Collection<CartItem> cartItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartSessionId() {
        return cartSessionId;
    }

    public void setCartSessionId(String cartSessionId) {
        this.cartSessionId = cartSessionId;
    }
    @Override
    public String toString() {
        return "rocks.j5.uga.expanse.model.Cart[ cart_id=" + getId() + " ]";
    } //toString


}