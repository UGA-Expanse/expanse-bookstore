package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.CartItemRepository;
import rocks.j5.uga.expanse.CartRepository;
import rocks.j5.uga.expanse.configuration.constants.Constants;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.Cart;
import rocks.j5.uga.expanse.model.CartItem;
import rocks.j5.uga.expanse.model.PriceDetail;
import rocks.j5.uga.expanse.repository.BookRepository;
import rocks.j5.uga.expanse.repository.PriceRepository;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Service
@Slf4j
public class CartItemService {

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

    public Cart create(Integer cartId,
                       Integer bookId,
                       HttpSession session) {

        Predicate<CartItem> bookExistsInCart = c -> c.getBook().getId().equals(bookId);

        if (cartRepository.existsById(cartId)) {
            Cart activeCart = (Cart) cartRepository.getById(cartId);
            activeCart.setCartSessionId(session.getId());
            Set<CartItem> cartItems = activeCart.getItems();
            if (cartItems.stream().anyMatch(bookExistsInCart)) {
                cartRepository.saveAndFlush(activeCart);
                return activeCart;
            }

            Book book = (Book) bookRepository.getById(bookId);
            BigDecimal price = (BigDecimal) priceRepository.findByBookId(bookId).get();
            CartItem cartItem = CartItem.builder().itemCount(1).cartItemType("NORMAL").book(book).price(price).build();
            cartRepository.save(activeCart);
            cartItem.setCart(activeCart);
            activeCart.getItems().add(cartItem);
            cartItemRepository.save(cartItem);

            return activeCart;
        }

        return create(bookId, session);
    }

    public Cart create(Integer bookId,
                       HttpSession session) {

        Integer cartId = (Integer) session.getAttribute(Constants.CART_SESSION_IDENT);

        if (cartId != null && cartRepository.getById(cartId) != null) {
            return create(cartId, bookId, session);
        }

        Set<CartItem> cartItems = new HashSet<>();
        Cart cart = Cart.builder().cartSessionId(session.getId()).items(cartItems).build();

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
                .build();

//        cartItem.setId();

        cart.getItems().add(cartItem);
        cartRepository.saveAndFlush(cart);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        return cart;
    }

    public Cart getCart(Integer cartId, HttpSession session) {
        return cartRepository.getById(cartId);
    }
}
