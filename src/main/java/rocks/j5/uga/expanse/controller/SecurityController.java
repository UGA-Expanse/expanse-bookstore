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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static rocks.j5.uga.expanse.configuration.constants.Constants.USER_PROFILE_IDENT;
import static rocks.j5.uga.expanse.configuration.constants.Constants.USER_PROFILE_PASSWORD_REPLACEMENT;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String process(Model model,
                          HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);

        return model.toString();
    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        if (messages == null) {
            messages = new ArrayList<>();
            request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        }
        messages.add(msg);
        request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        return "redirect:/";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    /**
     * Login user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping(value="/login")
    public @ResponseBody ResponseEntity<HttpResponseWrapper> login(@RequestBody final User user,
                                                                   HttpSession session)
    {
        try {
            User responseUser = null;
            HttpResponseWrapper response = null;
            String userIdent = (String) session.getAttribute(USER_PROFILE_IDENT);

            if (session.isNew() || userIdent == null || userIdent != user.getUsername()) {
                Optional<User> savedUserOptional = userService.verify(user);

                if (savedUserOptional.isPresent()) {
                    responseUser = savedUserOptional.get();
                    userIdent = responseUser.getUsername();
                    session.setAttribute(USER_PROFILE_IDENT, userIdent);
                    responseUser.setPassword(USER_PROFILE_PASSWORD_REPLACEMENT);
                    response = new HttpResponseWrapper(responseUser, null);
                } else {
                    throw new Exception("Login unsuccessful");
                }
            } else {
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

    /**
     * Clear session variables.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping(value="/logout")
    public @ResponseBody ResponseEntity<HttpResponseWrapper> logout(@RequestBody final User user,
                                                                   HttpSession session)
    {
        session.invalidate();
        HttpResponseWrapper response = new HttpResponseWrapper(user, null);
        return new ResponseEntity<HttpResponseWrapper>(response, HttpStatus.OK);
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
