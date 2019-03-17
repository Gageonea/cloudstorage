package agageonea.cloudstorage.domain.authentication;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = Token.TABLE_NAME)
public class Token {

    public static final String TABLE_NAME = "tokens";

    public Token(){}

    public Token(String token, Date expiresAt){
        this.token = token;
        this.expiresAt = expiresAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", length = 32)
    private String token;

    @Column(name = "expiresAt")
    private Date expiresAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
