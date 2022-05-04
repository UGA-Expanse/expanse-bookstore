package rocks.j5.uga.expanse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.j5.uga.expanse.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);

    Role save(Role role);
}
