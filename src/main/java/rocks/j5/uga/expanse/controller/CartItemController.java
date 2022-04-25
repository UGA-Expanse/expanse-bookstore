package rocks.j5.uga.expanse.controller;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.configuration.constants.Constants;
import rocks.j5.uga.expanse.domain.HttpResponseWrapper;
import rocks.j5.uga.expanse.model.Cart;
import rocks.j5.uga.expanse.model.CartItem;
import rocks.j5.uga.expanse.service.CartItemService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/api/cart", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartItemController {


    final private CartItemService cartItemService;


    public CartItemController(CartItemService cartItemService) {

        this.cartItemService = cartItemService;
    } // CartItemController


    @SneakyThrows
    @PostMapping(value = "/{cartId}/add/{bookId}")
    public @ResponseBody ResponseEntity<HttpResponseWrapper> add(@PathVariable final Long cartId,
                                                  @PathVariable final Integer bookId,
                                                  HttpSession session) {

        HttpResponseWrapper response = null;
        Cart cart = null;

        if (cartId == 0) {
            cart = cartItemService.create(bookId, session);
            session.setAttribute(Constants.CART_SESSION_IDENT, cart.getId());
        } else if (cartId != 0) {
            Long existingCartId = (Long) session.getAttribute(Constants.CART_SESSION_IDENT);
            if (existingCartId != cartId) {
                session.setAttribute(Constants.CART_SESSION_IDENT, cartId);
                cart = cartItemService.create(cartId, bookId, session);
            }
        }

        response = HttpResponseWrapper.builder().content(cart).encounteredErrors(new ArrayList<>()).build();

        return new ResponseEntity<HttpResponseWrapper>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/all")
    public @ResponseBody ResponseEntity<Set<CartItem>> getAll(HttpSession session) {

        HttpResponseWrapper response = null;
        Long cartId = (Long) session.getAttribute(Constants.CART_SESSION_IDENT);
        Cart cart = cartItemService.getCart(cartId, session);
        session.setAttribute(Constants.CART_SESSION_IDENT, cart.getId());
        Collection<CartItem> cartItems = cart.getCartItems();

        return new ResponseEntity<Set<CartItem>>(HttpStatus.OK);
    }

    @GetMapping(value = "/{cartId}")
    public @ResponseBody ResponseEntity<HttpResponseWrapper<Cart>> getAll(@PathVariable Long cartId,
                                                                          HttpSession session)
    {
        Cart cart = null;

        if (cartId == 0) {
            cart = cartItemService.getActiveCart();
        } else {
            cart = cartItemService.getCart(cartId, session);
        }

        session.setAttribute(Constants.CART_SESSION_IDENT, cart.getId());
        return new ResponseEntity<>(HttpResponseWrapper.<Cart>builder().content(cart).build(), HttpStatus.OK);
    }

    @GetMapping(value = "/{cartId}/cartitem/{cartItemId}")
    public @ResponseBody ResponseEntity<HttpResponseWrapper<Cart>> update(@NonNull @PathVariable Long cartId,
                                                                          @NonNull @PathVariable Integer cartItemId,
                                                                          @NonNull @RequestParam Integer qty,
                                                                          HttpSession session)
    {
        Cart cart = null;
        HttpResponseWrapper response = null;

       if (qty == 0) {
           cart = cartItemService.delete(cartId, cartItemId);
           cart = cartItemService.getCart(cartId, session);
       } else {
           cart = cartItemService.update(cartId, cartItemId, qty);
       }

        session.setAttribute(Constants.CART_SESSION_IDENT, cart.getId());
        return new ResponseEntity<>(HttpResponseWrapper.<Cart>builder().content(cart).build(), HttpStatus.OK);
    }

}
