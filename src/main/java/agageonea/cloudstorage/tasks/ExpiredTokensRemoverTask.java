package agageonea.cloudstorage.tasks;

import agageonea.cloudstorage.domain.authentication.Token;
import agageonea.cloudstorage.repositories.TokenRepository;
import agageonea.cloudstorage.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpiredTokensRemoverTask {

    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private TokenService tokenSrv;

    @Scheduled(cron = "0 */10 * * * *")
    public void cleanExpiredTokens(){
        List<Token> tokenList = tokenRepo.findAll();
        for(Token token : tokenList){
            if(!this.tokenSrv.validate(token)){
                tokenRepo.delete(token);
            }
        }

    }
}
