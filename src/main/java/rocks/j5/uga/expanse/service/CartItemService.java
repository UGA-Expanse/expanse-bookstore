package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.exception.BookNotFoundException;
import rocks.j5.uga.expanse.exception.PriceDetailNotFoundException;
import rocks.j5.uga.expanse.repository.CartItemRepository;
import rocks.j5.uga.expanse.repository.CartRepository;
import rocks.j5.uga.expanse.configuration.constants.Constants;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.Cart;
import rocks.j5.uga.expanse.model.CartItem;
import rocks.j5.uga.expanse.model.PriceDetail;
import rocks.j5.uga.expanse.repository.BookRepository;
import rocks.j5.uga.expanse.repository.PriceRepository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
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

    public CartItemService(CartItemRepository cartItemRepository,
                           PriceRepository priceRepository,
                           BookRepository bookRepository,
                           CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.priceRepository = priceRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.saveAndFlush(cartItem);
    }

    public Cart create(Long cartId,
                       Integer bookId,
                       HttpSession session) throws BookNotFoundException, PriceDetailNotFoundException {

        Predicate<CartItem> bookExistsInCart = c -> c.getBook().getId().equals(bookId);
        Function<Cart, Boolean> cartExistsInRepo = c -> cartRepository.getById(c.getId()) != null;
        Function<Long, Boolean> cartExistsInRepoById = c -> cartRepository.getById(c) != null;

        if (cartRepository.existsById(cartId)) {
            Long cartIdL = Long.valueOf(cartId);
            Cart cart = (Cart) cartRepository.getCartById(cartIdL);
            if (cart == null) {
                return create(bookId, session);
            }

            cart.setCartSessionId(session.getId());
            Set<CartItem> cartItems = cart.getCartItems();
            if (cartItems.stream().anyMatch(bookExistsInCart)) {
                return cartRepository.saveAndFlush(cart);
            }

            Book book = (Book) bookRepository.getById(bookId);
            if (book == null) {
                throw new BookNotFoundException("Book was not found in database: " + bookId);
            }

            BigDecimal price = (BigDecimal) priceRepository.findByBookId(bookId).get();
            if (price == null) {
                throw new PriceDetailNotFoundException("Price for book was not found in database" + bookId);
            }

            CartItem cartItem = CartItem.builder().quantity(1).cartItemType("NORMAL").book(book).price(price).build();
            cartItem.setCart(cart);

//            cartRepository.save(cart);
//            cart.getCartItems().add(cartItem);

            cartItemRepository.saveAndFlush(cartItem);

            return cart;
        }

        return create(bookId, session);
    }

                                            //q

    public Cart create(Integer bookId,
                       HttpSession session)
            throws PriceDetailNotFoundException, BookNotFoundException {

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
                .price(price)
                .cart(cart)
                .build();

        cartItemRepository.save(cartItem);

        cart.getCartItems().add(cartItem);

        // cartItem.setCart(cart);
        // cartItemRepository.save(cartItem);

        return cart;
    }

    public Cart getCart(Long cartId, HttpSession session) {
        return cartRepository.getById(cartId);
    }
}
