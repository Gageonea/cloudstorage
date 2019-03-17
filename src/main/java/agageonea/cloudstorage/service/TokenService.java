package agageonea.cloudstorage.service;

import agageonea.cloudstorage.domain.authentication.Token;
import agageonea.cloudstorage.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepo;

    private Integer tokenValidity;

    public TokenService(@Value("${app.security.token.validity}") Integer minutes){
        this.tokenValidity = minutes;
    }

    public boolean validate(String tokenString){
        if(tokenString == null || tokenString.isEmpty()){
            return false;
        }
        Token token = this.tokenRepo.findByToken(tokenString);
        if(token == null){
            return false;
        }

        return this.validate(token);
    }

    public Token extendValability(Token token) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, this.tokenValidity);
        Date expiresAt = calendar.getTime();

        token.setExpiresAt(expiresAt);

        return tokenRepo.save(token);
    }

    public boolean validate(Token token) {
        Date expiresAt = token.getExpiresAt();
        Date now = new Date();

        if(now.after(expiresAt)){
            return false;
        }

        return true;
    }
}
