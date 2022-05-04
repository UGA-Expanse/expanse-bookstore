package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.model.Category;
import rocks.j5.uga.expanse.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
    Customer findByUseridIgnoreCase(String userid);


    @Override
    Optional<Customer> findById(Integer integer);
}
