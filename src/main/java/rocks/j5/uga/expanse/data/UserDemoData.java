package rocks.j5.uga.expanse.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import rocks.j5.uga.expanse.ExpanseBookstoreApplication;
import rocks.j5.uga.expanse.model.User;
import rocks.j5.uga.expanse.repository.UserRepository;

@Slf4j
@Component
public class UserDemoData {

    private UserRepository repository;

    @Autowired
    public UserDemoData(UserRepository userRepository) {
        this.repository = userRepository;
//        demo();
    }

    public void demo() {
        repository.save(new User("user2", "user2@j5.rocks", "user2", false));
        repository.save(new User("user3", "user3@j5.rocks", "user3", false));
        repository.save(new User("user4", "user4@j5.rocks", "user4", false));
        repository.save(new User("user5", "user5@j5.rocks", "user5", false));
        repository.save(new User("user6", "user6@j5.rocks", "user6", false));
        repository.save(new User("user7", "user7@j5.rocks", "user7", false));

        log.info("Users found with findAll():");
        log.info("----------------------------");
        for (User user : repository.findAll()) {
            log.info(user.toString());
        }
        log.info("");

        // fetch an individual user by ID
        User user = repository.findById("user1").orElse(null);
        log.info("User found with findById('user1'");
        log.info("----------------------------");
        log.info(user.toString());
        log.info("");


    }
}
