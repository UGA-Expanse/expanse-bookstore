package rocks.j5.uga.expanse.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @GetMapping("/")
    public @ResponseBody ResponseEntity<List> getMessage(Model model,
                                                         HttpSession session) {

        List greetings = (List) session.getAttribute("GREETING_MESSAGES");
        if (greetings == null) {
            greetings = new ArrayList<>();
        }

        return new ResponseEntity<List>(greetings, HttpStatus.OK);
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<List> saveMessage(@RequestParam("message") String greeting, HttpServletRequest request)
    {
        List greetings = (List) request.getSession().getAttribute("GREETING_MESSAGES");
        if(greetings == null) {
            greetings = new ArrayList<>();
            request.getSession().setAttribute("GREETING_MESSAGES", greetings);
        }
        greetings.add(greeting);
        return new ResponseEntity<List>(greetings,HttpStatus.OK);
    }
}
