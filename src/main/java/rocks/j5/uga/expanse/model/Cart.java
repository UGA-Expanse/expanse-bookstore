package rocks.j5.uga.expanse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.EAGER)
    private Collection<CartItem> cartItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cart_life", columnDefinition = "ENUM('EPHEMERAL', 'PERSISTENT')")
    @Enumerated(EnumType.STRING)
    private CartLife cartLife;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinFormula("(" +
//            "SELECT cart_id " +
//            "FROM cart " +
//            "ORDER BY cart_id DESC " +
//            "LIMIT 1" +
//            ")")
//    private Long latestCartId;

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



//    @OneToMany
//    @JoinColumn(name = "author_id", referencedColumnName = "id")
//    @Where(clause = "date_published = (SELECT MAX(book.date_published) " +
//            "FROM book" +
//            " where author_id = book.author_id)")
//    @OrderBy("datePublished Desc")
//    private List<Book> books;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinFormula("(" +
//            "SELECT b.id " +
//            "FROM book b " +
//            "WHERE b.author_id = id " +
//            "ORDER BY b.date_published DESC " +
//            "LIMIT 1" +
//            ")")
//    private Book latestBook;
}


