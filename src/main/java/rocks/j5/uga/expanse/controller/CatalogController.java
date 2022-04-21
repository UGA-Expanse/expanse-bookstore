package rocks.j5.uga.expanse.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.domain.EncounteredError;
import rocks.j5.uga.expanse.domain.HttpResponseWrapper;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.BookOriginal;
import rocks.j5.uga.expanse.model.User;
import rocks.j5.uga.expanse.service.CatalogService;
import rocks.j5.uga.expanse.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/book", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://j5.rocks", maxAge = 3600)
public class                                                                                                                                                                                            CatalogController {

    private final CatalogService catalogService;
    private final UserService userService;

    public CatalogController(CatalogService catalogService,
                             UserService userService) {

        this.catalogService = catalogService;
        this.userService = userService;
    }


    /**
     * Gets all books.
     *
     * @return all books
     */
    @GetMapping(value = "/all")
    public List<Book> getAll(HttpSession session) {
        return catalogService.findAll();
    }

    /**
     * Gets all books.
     *
     * @return all books
     */
    @GetMapping(value = "/category/{categoryName}/all")
    public List<Book> getAllByCategory(@PathVariable String categoryName, HttpSession session) {
        return catalogService.findAllByCategory(categoryName);
    }

    /**
     * Get a specific book by isbn
     *
     * @return Book
     */
    @GetMapping(value = "/bd/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn, HttpSession session) {
        return catalogService.findBookByIsbn(isbn);
    }

    /**
     * Gets all books.
     *
     * @return all books
     */
//    @GetMapping(value = "/search")
//    public List<Book> searchByTerm(@RequestParam String term) {
////        return catalogService.findAllBySearch(term);
//        return catalogService.findAllUsingContains(term);
//    }

    /**
     * Gets all books.
     *
     * @return all books
     */
    @GetMapping(value = "/search")
    public List<Book> searchByTerm(@RequestParam String term,
                                   @RequestParam String criteria, HttpSession session) {
        if (criteria == null)
            return catalogService.findAllUsingContains(term);
        else
            return catalogService.findAllUsingCriteria(term, criteria);
    }

    /**
     * Gets book.
     *
     * @return book if exists
     */
    @GetMapping(value = "/get")
    public BookOriginal get(@RequestParam("id") int id, HttpSession session) {
        return null;
    }





    /**
     * Login user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping
    public @ResponseBody ResponseEntity<HttpResponseWrapper> login(@RequestBody final User user,
                                                                   HttpSession session)
    {
        try {
            User responseUser = null;
            HttpResponseWrapper response = null;
            String userIdent = (String) session.getAttribute("ACCOUNT_USERNAME");

            if (session.isNew() || userIdent == null || userIdent != user.getUsername()) {
                Optional<User> savedUserOptional = userService.verify(user);
                if (savedUserOptional.isPresent()) {
                    responseUser = savedUserOptional.get();
                    userIdent = responseUser.getUsername();
                    session.setAttribute("ACCOUNT_USERNAME", userIdent);
                    response = new HttpResponseWrapper(responseUser, null);
                } else {
                    throw new Exception("Login unsuccessful");
                }
            } else {
//                String message = "User is active currently.";
                responseUser = userService.get(userIdent);
                response = new HttpResponseWrapper(responseUser, null);
            }

            return new ResponseEntity<HttpResponseWrapper>(
                    response,
                    HttpStatus.OK
            );
        } catch(Exception e) {
            EncounteredError eError = new EncounteredError(e.getMessage(), 0);
            return new ResponseEntity<HttpResponseWrapper>(
                    new HttpResponseWrapper(null, List.of(eError)),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List> getMessage(Model model,
                                                         HttpSession session) {

        List greetings = (List) session.getAttribute("GREETING_MESSAGES");
        if (greetings == null) {
            greetings = new ArrayList<>();
        }

        return new ResponseEntity<List>(greetings, HttpStatus.OK);
    }
}
