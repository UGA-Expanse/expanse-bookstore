package rocks.j5.uga.expanse.service;

import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.model.UserO;
import rocks.j5.uga.expanse.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceO {

    /** The user repository. */
    private final UserRepository userRepository;

    public UserServiceO(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    /**
     * Login user.
     *
     * @param userO the User object for the User
     * @return user if username and password matches
     */
    public Optional<UserO> verify(UserO userO)
    {
        try {
            UserO dbUserRecordO = userRepository.findById(userO.getUsernameO()).orElse(null);
            if (dbUserRecordO == null || dbUserRecordO.getUsernameO() == null || dbUserRecordO.getPassword() == null) {
                throw new EntityNotFoundException("User partially found");
            }
            boolean equalCredentials = userO.getPassword().equals(dbUserRecordO.getPassword());
            return (equalCredentials) ? Optional.of(dbUserRecordO) : Optional.empty();
        } catch (EntityNotFoundException enfE) {
            return Optional.empty();
        }
    }

    /**
     * Gets all users.
     *
     * @return all users
     */
    public List<UserO> findAll() {
        return userRepository.findAll();
    }

    /**
     * Gets user.
     *
     * @param username the identifier for the User
     * @return user if exists
     */
    public UserO get(String username) {
        return userRepository.findById(username).get();
    }

    /**
     * Gets all users.
     *
     * @return Collection<user> All users in the Table
     */
    public List<UserO> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Persist user.
     *
     * @param userO the user object that was persisted.
     * @return the user
     */
    public UserO persist(final UserO userO) {
        userRepository.save(userO);
        return userRepository.findById(userO.getUsernameO()).get();
    }

    /**
     * Delete user.
     *
     * @param username the email
     * @return all users
     */
    public List<UserO> delete(String username) {
        userRepository.deleteById(username);
        return userRepository.findAll();
    }

    /**
     * Put user.
     *
     * @param username 	the username
     * @param userO  the user
     * @return all users
     */
    public List<UserO> update(String username,
                              UserO userO)
    {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
            userRepository.save(userO);
        }

        return userRepository.findAll();
    }
}
