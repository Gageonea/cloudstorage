package agageonea.cloudstorage.factories;

import agageonea.cloudstorage.domain.authentication.Token;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

public class TokenFactory {
    public static Token getToken(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        String token = bytes.toString();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        Date expiresAt = calendar.getTime();

        return new Token(token, expiresAt);
    }
}
