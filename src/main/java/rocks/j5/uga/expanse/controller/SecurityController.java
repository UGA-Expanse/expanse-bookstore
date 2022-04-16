package rocks.j5.uga.expanse.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.domain.EncounteredError;
import rocks.j5.uga.expanse.domain.HttpResponseWrapper;
import rocks.j5.uga.expanse.model.User;
import rocks.j5.uga.expanse.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
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
