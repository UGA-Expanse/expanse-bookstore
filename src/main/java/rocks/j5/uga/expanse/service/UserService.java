package rocks.j5.uga.expanse.service;

import rocks.j5.uga.expanse.domain.Role;
import rocks.j5.uga.expanse.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String roleName, String username);
    User getUser(String username);
    List<User> getUsers();
}
