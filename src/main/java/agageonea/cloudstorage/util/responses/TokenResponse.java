package agageonea.cloudstorage.util.responses;

import agageonea.cloudstorage.domain.authentication.Token;
import agageonea.cloudstorage.util.enums.StatusEnum;

import java.util.Date;

public class TokenResponse extends Response {

    public TokenResponse(Token token){
        this.status = StatusEnum.SUCCESS;
        this.token = token.getToken();
        this.expiresAt = token.getExpiresAt();
    }

    private String token;
    private Date expiresAt;

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
