package rocks.j5.uga.expanse.controller;

//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csrf")
@CrossOrigin("*")
public class CsrfController {

//    public CsrfToken csrf(CsrfToken token) {
//        return token;
//    }
}
