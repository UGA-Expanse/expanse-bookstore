package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.j5.uga.expanse.exception.BookNotFoundException;
import rocks.j5.uga.expanse.exception.PriceDetailNotFoundException;
import rocks.j5.uga.expanse.model.*;
import rocks.j5.uga.expanse.repository.*;
import rocks.j5.uga.expanse.configuration.constants.Constants;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Slf4j
public class CartItemService {

    EntityManager entityManager;
    private final CartItemRepository cartItemRepository;
    private final PriceRepository priceRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    public CartItemService(CartItemRepository cartItemRepository,
                           PriceRepository priceRepository,
                           BookRepository bookRepository,
                           CartRepository cartRepository,
                           CustomerRepository customerRepository)
    {
        this.cartItemRepository = cartItemRepository;
        this.priceRepository = priceRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.saveAndFlush(cartItem);
    }

    public Cart create(Long cartId,
                       Integer bookId,
                       HttpSession session) throws Throwable {

        Predicate<CartItem> bookExistsInCart = c -> c.getBook().getId().equals(bookId);
        Function<Cart, Boolean> cartExistsInRepo = c -> cartRepository.getById(c.getId()) != null;
        Function<Long, Boolean> cartExistsInRepoById = c -> cartRepository.getById(c) != null;

        Cart aCart = getCartByUser((String) session.getAttribute("USERNAME"), cartId);
        if (aCart != null) {
            Long cartIdL = Long.valueOf(cartId);
            // Cart cart = (Cart) cartRepository.getCartById(cartIdL);
            //if (cart == null) {
//                return create(bookId, session);
//            }

//            aCart.setCartSessionId(session.getId());
            if (aCart.getCartItems().stream().anyMatch(bookExistsInCart)) {
                //cartRepository.saveAndFlush(cart);
                return aCart;
            }

            Book book = (Book) bookRepository.findByBookId(bookId);
            if (book == null) throw new BookNotFoundException("Book was not found in database: " + bookId);

            BigDecimal price = ((PriceDetail) priceRepository.findByBookId(bookId).get()).getListPrice();
            if (price == null) {
                throw new PriceDetailNotFoundException("Price for book was not found in database" + bookId);
            }

            CartItem cartItem = CartItem.builder().quantity(1).cartItemType("NORMAL").book(book).price(price).build();
            cartItem.setCart(aCart);

            cartItemRepository.saveAndFlush(cartItem);

            aCart.getCartItems().add(cartItem);

            return aCart;
        }

        return create(bookId, session);
    }

    /**
     * Create Cart from session_id or get from authenticate user table
     * @param owner
     * @param cartIdent
     * @return
     * @throws Throwable
     */
    public Cart create(String cartIdent, String owner) throws Throwable
    {
        if (!owner.equals("anonymousUser")) {
            Optional<Cart> cart = cartRepository.findByCustomerUserid(owner);
            if (cart.isPresent()) {
                return cart.get();
            }

            Customer customer = customerRepository.findByUseridIgnoreCase(owner);
            if (customer == null) {
                customer = customerRepository.save(Customer.builder().userid(owner).build());
            }
            Cart c = cartRepository.save(Cart.builder().cartLife(CartLife.PERSISTENT).cartSessionId(cartIdent).active(true).customer(customer).build());
            return c;
        }

        Customer aCustomer = customerRepository.findById(10000).get();
        Cart c1 = cartRepository.save(Cart.builder().cartLife(CartLife.EPHEMERAL).cartSessionId(cartIdent).active(true).customer(aCustomer).build());
        return  c1;
    }

    public Cart create(Integer bookId, HttpSession session)
        throws Throwable
    {

    Long cartId = (Long) session.getAttribute(Constants.CART_SESSION_IDENT);

    if (cartId != null && cartRepository.getById(cartId) != null) {
        return create(cartId, bookId, session);
    }

    Set<CartItem> cartItems = new HashSet<>();
    Cart cart = Cart.builder().cartSessionId(session.getId()).cartItems(cartItems).build();
    cartRepository.save(cart);

    CartItem cartItem;
    BigDecimal price = null;
    Optional<PriceDetail>  priceDetail= priceRepository.findFirstByBook_Id(bookId);
    if (priceDetail.isPresent()) {
        price = priceDetail.get().getListPrice();
    }

    Book book = (Book) bookRepository.findById(bookId).get();
    cartItem = CartItem.builder()
            .cartItemType("NORMAL")
            .book(book)
            .quantity(1)
            .price(price)
            .cart(cart)
            .build();

    cartItemRepository.save(cartItem);

    cart.getCartItems().addAll(cartItemRepository.findByCart_Id(cart.getId()));

    // cartItem.setCart(cart);
    // cartItemRepository.save(cartItem);

    return cart;
}

    public Cart getActiveCart()
    {
        return cartRepository.findFirstByIdGreaterThanOrderByIdDesc(0L);
    }

    public Cart getCart(Long cartId,
                        HttpSession session)
    {
        Cart c = cartRepository.getById(cartId);
        return (c.isActive()) ? c : null;
    }

    @Transactional
    public void delete(Long cartId,
                       Integer cartItemId) {
        // todo test that cart belongs to user.
        Cart c = cartRepository.getCartById(cartId);
        CartItem cartItem = c.getCartItems().stream().filter(cart -> cart.getCart_item_id().equals(cartItemId)).findFirst().orElse(null);
        if (c != null) {
            cartItemRepository.deleteById(cartItemId);
        }
    }

    public Cart update(Long cartId,
                       Integer cartItemId,
                       int qty,
                       HttpSession session)
    {
        // todo test that cart belongs to user.
        Cart c = getCartByUser((String)session.getAttribute("USERNAME"), cartId); //cartRepository.getCartById(cartId);
        if (c != null) {
            CartItem cartItem = c.getCartItems().stream().filter(cart -> cart.getCart_item_id().equals(cartItemId)).findFirst().orElse(null);
            if (cartItem != null) {
                cartItem.setQuantity(qty);
                c.setActive(true);
                cartRepository.saveAndFlush(c);
                return c;
            }
        }

        return null;
    }

    public Cart getCartByUser(String username,
                              Long cartId)
    {
        Cart c = cartRepository.getCartById(cartId);
        if (c == null) {
            return null;
        } else if (c.getCustomer() != null && !username.equals(c.getCustomer().getUserid()) && "anonymousUser".equals(c.getCustomer().getUserid())) {
            Cart userCart = cartRepository.findByCustomerUserid(username).orElse(null);
            if (userCart != null && userCart.getId() != c.getId() && userCart.isActive()) {
                userCart.getCartItems().addAll(c.getCartItems());
                c.setActive(false);
                c.setCartLife(CartLife.PERSISTENT);
                cartRepository.saveAll(List.of(userCart, c));
                return userCart;
            }
            return null;
        } else if (c != null) {
            return c;
        }
        else if (c.getCustomer() != null && !username.equals(c.getCustomer().getUserid()) || !c.isActive()) {
            return null;
        }

        return c;
    }

    public Cart getCart(String cartIdent, String username) {
        Optional<Cart> c = cartRepository.findByCartSessionId(cartIdent);
        if (c.isPresent()) {
            Cart c1 = c.get();
            if (username.equals(c1.getCustomer().getUserid()) && c1.isActive()) {
                return c1;
            }
        }

        return null;
    }

    @Transactional
    public void invalidateCart(Cart cart) {
        if (cart.getCustomer() == null || "anonymousUser".equals(cart.getCustomer().getUserid())) {
            Cart cartRef = cartRepository.getCartById(cart.getId());
            cartRef.setActive(false);
        }
    }
}
