package rocks.j5.uga.expanse.controller;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rocks.j5.uga.expanse.component.IAuthenticationFacade;
import rocks.j5.uga.expanse.configuration.constants.Constants;
import rocks.j5.uga.expanse.domain.HttpResponseWrapper;
import rocks.j5.uga.expanse.model.Cart;
import rocks.j5.uga.expanse.model.CartItem;
import rocks.j5.uga.expanse.service.CartItemService;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/api/cart", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartItemController
{
    final private CartItemService cartItemService;
    private IAuthenticationFacade authenticationFacade;

    public CartItemController(CartItemService cartItemService,
                              IAuthenticationFacade authenticationFacade)
    {
        this.cartItemService = cartItemService;
        this.authenticationFacade = authenticationFacade;
    } // CartItemController

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple()
    {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }


    @SneakyThrows
    @PostMapping(value = "/{cartId}/add/{bookId}")
    public @ResponseBody ResponseEntity<HttpResponseWrapper> add(@PathVariable final Long cartId,
                                                                 @PathVariable final Integer bookId,
                                                                 @CookieValue(name = "session", required = false) String cookie,
                                                                 HttpSession session) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        session.setAttribute("USERNAME", username);
        session.setAttribute("CART_IDENT", session.getId());

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

    @SneakyThrows
    @PostMapping(value = "/create")
    public @ResponseBody ResponseEntity<HttpResponseWrapper<Cart>> getOrCreate(@CookieValue(required = false) String cartIdent,
                                                                               @CookieValue(name = "SESSION", required = false) String sess,
                                                                               HttpSession session)
    {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        cartIdent = (cartIdent == null) ? session.getId() : cartIdent;
        Cart cart = cartItemService.create(cartIdent, username);
        String path = "/api/cart/create/".concat(String.valueOf(1));

        session.setAttribute(Constants.CART_SESSION_IDENT, cart.getId());
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString());
        return new ResponseEntity<>(HttpResponseWrapper.<Cart>builder().content(cart).build(), HttpStatus.CREATED);
//        return ResponseEntity.created(uri).body(cart);

//        Cart cart = null;
//
//        if (cartId == 0 && username.equals("anonymousUser")) {
////            cartItemService.createCart()
//        } else if (cartId == 0 && username != "anonymousUser") {
////            cartItemService.getActiveCart()
//        } else if (cartId > 0 && username == "anonymousUser") {
////            cartItemService.getCartBySessionId()
//        } else if (cartId > 0 && username != "anonymousUser") {
//            cart = cartItemService.getCartByUser(username, cartId);
//        } else {
//            cart = cartItemService.getCart(cartId, session);
//        }
//
//        session.setAttribute(Constants.CART_SESSION_IDENT, cart.getId());
//
//        return new ResponseEntity<>(HttpResponseWrapper.<Cart>builder().content(cart).build(), HttpStatus.OK);
    }

    @GetMapping(value = "/{cartId}")
    public @ResponseBody ResponseEntity<HttpResponseWrapper<Cart>> getAll(@PathVariable Long cartId,
                                                                          @CookieValue(required = true) String cartIdent,
                                                                          @CookieValue(name = "SESSION", required = false) String sess,
                                                                          HttpSession session) throws Throwable
    {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Cart cart = null;

        if (cartId == 0) {
//            cartItemService.create(username, cartIdent);
        } else if (username != "anonymousUser") {
            cart = cartItemService.getCartByUser(username, cartId);
        } else {
            cart = cartItemService.getCart(cartIdent, username);
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
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        session.setAttribute("USERNAME", username);
        session.setAttribute("CART_IDENT", session.getId());

        Cart cart = null;
        HttpResponseWrapper response = null;

       if (qty == 0) {
           cartItemService.delete(cartId, cartItemId);
           cart = cartItemService.getCart(cartId, session);
       } else {
           cart = cartItemService.update(cartId, cartItemId, qty, session);
       }

//        session.setAttribute(Constants.CART_SESSION_IDENT, cart.getId());
        return new ResponseEntity<>(HttpResponseWrapper.<Cart>builder().content(cart).build(), HttpStatus.OK);
    }

}
