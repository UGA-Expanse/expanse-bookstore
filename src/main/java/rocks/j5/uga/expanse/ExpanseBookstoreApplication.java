package rocks.j5.uga.expanse;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import rocks.j5.uga.expanse.domain.Role;
import rocks.j5.uga.expanse.service.UserService;

@SpringBootApplication
public class ExpanseBookstoreApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ExpanseBookstoreApplication.class, args);
	}
}
