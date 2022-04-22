package rocks.j5.uga.expanse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer cart_item_id;

    @Column(name = "cart_item_type", length = 20)
    private String cartItemType;

    @JsonIgnore
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "item_count")
    private Integer quantity;

    @Column(name = "price", precision = 5, scale = 2)
    private BigDecimal price;

    public String getCartItemType() {
        return cartItemType;
    }

    public void setCartItemType(String cartItemType) {
        this.cartItemType = cartItemType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "rocks.j5.uga.expanse.model.CartItem[ cart_item_id=" + getCart_item_id() + " ]";
    } //toString

}