package rocks.j5.uga.expanse.service;

import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.data.UserDemoData;
import rocks.j5.uga.expanse.model.User;
import rocks.j5.uga.expanse.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    /** The user repository. */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    /**
     * Login user.
     *
     * @param user the User object for the User
     * @return user if username and password matches
     */
    public Optional<User> verify(User user)
    {
        try {
            User dbUserRecord = userRepository.findById(user.getUsername()).orElse(null);
            if (dbUserRecord == null || dbUserRecord.getUsername() == null || dbUserRecord.getPassword() == null) {
                throw new EntityNotFoundException("User partially found");
            }
            boolean equalCredentials = user.getPassword().equals(dbUserRecord.getPassword());
            return (equalCredentials) ? Optional.of(dbUserRecord) : Optional.empty();
        } catch (EntityNotFoundException enfE) {
            return Optional.empty();
        }
    }

    /**
     * Gets all users.
     *
     * @return all users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Gets user.
     *
     * @param username the identifier for the User
     * @return user if exists
     */
    public User get(String username) {
        return userRepository.findById(username).get();
    }

    /**
     * Gets all users.
     *
     * @return Collection<user> All users in the Table
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Persist user.
     *
     * @param user the user object that was persisted.
     * @return the user
     */
    public User persist(final User user) {
        userRepository.save(user);
        return userRepository.findById(user.getUsername()).get();
    }

    /**
     * Delete user.
     *
     * @param username the email
     * @return all users
     */
    public List<User> delete(String username) {
        userRepository.deleteById(username);
        return userRepository.findAll();
    }

    /**
     * Put user.
     *
     * @param username 	the username
     * @param user  the user
     * @return all users
     */
    public List<User> update(String username,
                             User user)
    {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
            userRepository.save(user);
        }

        return userRepository.findAll();
    }
}
