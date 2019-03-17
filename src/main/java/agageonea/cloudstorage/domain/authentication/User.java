package agageonea.cloudstorage.domain.authentication;

import agageonea.cloudstorage.domain.abstracts.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = User.TABLE_NAME)
public class User extends AbstractEntity {

    public static final String TABLE_NAME = "users";

    public User(){}

    @Column(name = "username", length = 32)
    private String username;

    @Column(name = "email", length = 64, unique = true)
    @Email
    private String email;

    @Column(name = "password", length = 256)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, @Email String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
