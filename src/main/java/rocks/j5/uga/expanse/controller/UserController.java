package rocks.j5.uga.expanse.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.model.UserO;
import rocks.j5.uga.expanse.service.UserServiceO;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://j5.rocks", maxAge = 3600)
public class UserController {

    private final UserServiceO userServiceO;

    public UserController(UserServiceO userServiceO) {
        this.userServiceO = userServiceO;
    }

    /**
     * Login user.
     *
     * @param userO the user
     * @return the user
     */
    @PostMapping(value = "/check")
    public @ResponseBody ResponseEntity<UserO> login(@RequestBody final UserO userO,
                                                     HttpSession session)
    {
        UserO responseUserO;
        UserO userOSessionInfo = (UserO) session.getAttribute("USER_INFO");

        if (userOSessionInfo == null) {
            Optional<UserO> savedUserOptional = userServiceO.verify(userO);
            savedUserOptional.ifPresent(retUser -> session.setAttribute("USER_INFO", retUser));
            responseUserO = savedUserOptional.orElse(null);
        } else {
            responseUserO = userOSessionInfo;
        }

        return new ResponseEntity<UserO>(responseUserO, HttpStatus.OK);
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
    public @ResponseBody ResponseEntity<List<UserO>> getAll(HttpSession session)
    {
        List<UserO> response = userServiceO.findAll();
        return new ResponseEntity<List<UserO>>(response, HttpStatus.OK);
    }

    /**
     * Gets user.
     *
     * @param username
     * @return user if exists
     */
    @GetMapping(value = "/{username}")
    public @ResponseBody ResponseEntity<UserO> get(@PathVariable("username") String username,
                                                   HttpSession session)
    {
        return new ResponseEntity<UserO>(userServiceO.get(username), HttpStatus.OK);
    }

    /**
     * Persist user.
     *
     * @param userO the user
     * @return the user
     */
    @PostMapping(value = "/add")
    public  @ResponseBody ResponseEntity<UserO> persist(@RequestBody final UserO userO,
                                                        HttpSession session)
    {
        return new ResponseEntity<UserO>(userServiceO.persist(userO), HttpStatus.OK);
    }

    /**
     * Delete user.
     *
     * @param username the email
     * @return all users
     */
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<List<UserO>> delete(@PathVariable String username, HttpSession session) {
        return new ResponseEntity<List<UserO>>(userServiceO.delete(username), HttpStatus.OK);
    }

    /**
     * Put user.
     *
     * @param username 	the username
     * @param userO  the user
     * @return all users
     */
    @PutMapping(value = "/{username}/put")
    public @ResponseBody ResponseEntity<List<UserO>> put(@PathVariable String username,
                                                         @RequestBody UserO userO,
                                                         HttpSession httpSession) {
        return new ResponseEntity<List<UserO>>(userServiceO.update(username, userO), HttpStatus.OK);
    }
}
