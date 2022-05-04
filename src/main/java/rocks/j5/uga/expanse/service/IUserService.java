package rocks.j5.uga.expanse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rocks.j5.uga.expanse.domain.Role;
import rocks.j5.uga.expanse.domain.User;
import rocks.j5.uga.expanse.repository.RoleRepo;
import rocks.j5.uga.expanse.repository.UserRepo;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class IUserService implements UserService, UserDetailsService
{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not foundin the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user)
    {
        log.info("Saving new user {} to database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role)
    {
        log.info("Saving new role {} to database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String roleName, String username)
    {
        log.info("Saving new role {} to user {} to database", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        Set<Role> roles = (user.getRoles() == null) ? new HashSet<>() : user.getRoles();
        roles.add(role);
    }

    @Override
    public User getUser(String username)
    {
        log.info("Fetching user {} from database", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers()
    {
        log.info("Fetching all users from database");
        return userRepo.findAll();
    }
}
