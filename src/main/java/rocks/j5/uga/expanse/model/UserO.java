package rocks.j5.uga.expanse.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class User.
 *
 * @author nik nab
 * @version 1.0
 */

@Data
@Entity
@Table(name = "usero")
public class UserO {

    /** The id. */
    @Id
    @Column(unique = true, nullable = false)
    private String usernameO;

    /** The email. */
    @Column(unique = true, nullable = false)
    private String email;

    /** The password. */
    @Column(nullable = false)
    private String password;

    /** The is admin. */
    @Column(nullable = false)
    private boolean isAdmin;

    /**
     * Empty Constructor. Instantiates a new user.
     */
    public UserO() {
    }

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     * @param isAdmin  the is admin
     */
    public UserO(String username, String email, String password, boolean isAdmin)
    {
        this.usernameO = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsernameO() {
        return usernameO;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsernameO(String username) {
        this.usernameO = username;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Checks if is admin.
     *
     * @return true, if is admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin.
     *
     * @param isAdmin the new admin
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
