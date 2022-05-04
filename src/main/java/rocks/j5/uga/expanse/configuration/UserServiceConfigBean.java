package rocks.j5.uga.expanse.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rocks.j5.uga.expanse.service.UserService;

@Configuration
@RequiredArgsConstructor
public class UserServiceConfigBean
{
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public CommandLineRunner run(UserService userService)
    {
        return args -> {};
    }
}
