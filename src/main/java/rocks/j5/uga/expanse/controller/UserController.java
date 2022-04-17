package rocks.j5.uga.expanse.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.model.User;
import rocks.j5.uga.expanse.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Login user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping(value = "/check")
    public @ResponseBody ResponseEntity<User> login(@RequestBody final User user,
                                                     HttpSession session)
    {
        User responseUser;
        User userSessionInfo = (User) session.getAttribute("USER_INFO");

        if (userSessionInfo == null) {
            Optional<User> savedUserOptional = userService.verify(user);
            savedUserOptional.ifPresent(retUser -> session.setAttribute("USER_INFO", retUser));
            responseUser = savedUserOptional.orElse(null);
        } else {
            responseUser = userSessionInfo;
        }

        return new ResponseEntity<User>(responseUser, HttpStatus.OK);
    }

    @GetMapping("/*")
    public @ResponseBody ResponseEntity<List> getMessage(Model model,
                                                         HttpSession session) {

        List greetings = (List) session.getAttribute("GREETING_MESSAGES");
        if (greetings == null) {
            greetings = new ArrayList<>();
        }

        return new ResponseEntity<List>(greetings, HttpStatus.OK);
    }

    /**
     * Gets all users.
     *
     * @return all users
     */
    @GetMapping(value = "/all")
    public @ResponseBody ResponseEntity<List<User>> getAll(HttpSession session)
    {
        List<User> response = userService.findAll();
        return new ResponseEntity<List<User>>(response, HttpStatus.OK);
    }

    /**
     * Gets user.
     *
     * @param username
     * @return user if exists
     */
    @GetMapping(value = "/{username}")
    public @ResponseBody ResponseEntity<User> get(@PathVariable("username") String username,
                                                  HttpSession session)
    {
        return new ResponseEntity<User>(userService.get(username), HttpStatus.OK);
    }

    /**
     * Persist user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping(value = "/add")
    public  @ResponseBody ResponseEntity<User> persist(@RequestBody final User user,
                                                       HttpSession session)
    {
        return new ResponseEntity<User>(userService.persist(user), HttpStatus.OK);
    }

    /**
     * Delete user.
     *
     * @param username the email
     * @return all users
     */
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<List<User>> delete(@PathVariable String username) {
        return new ResponseEntity<List<User>>(userService.delete(username), HttpStatus.OK);
    }

    /**
     * Put user.
     *
     * @param username 	the username
     * @param user  the user
     * @return all users
     */
    @PutMapping(value = "/{username}/put")
    public @ResponseBody ResponseEntity<List<User>> put(@PathVariable String username,
                                                        @RequestBody User user,
                                                        HttpSession httpSession) {
        return new ResponseEntity<List<User>>(userService.update(username, user), HttpStatus.OK);
    }
}
