package agageonea.cloudstorage.controller.authentication;

import agageonea.cloudstorage.domain.authentication.Token;
import agageonea.cloudstorage.domain.authentication.User;
import agageonea.cloudstorage.factories.TokenFactory;
import agageonea.cloudstorage.repositories.TokenRepository;
import agageonea.cloudstorage.repositories.UserRepository;
import agageonea.cloudstorage.service.TokenService;
import agageonea.cloudstorage.util.responses.ErrorResponse;
import agageonea.cloudstorage.util.responses.Response;
import agageonea.cloudstorage.util.enums.StatusEnum;
import agageonea.cloudstorage.util.responses.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private TokenService tokenSrv;



    @PostMapping("/signup")
    public @ResponseBody Response signUp(@RequestBody User newUser){
        Response response;
        try {
            this.userRepo.save(newUser);

            response = new Response();
            response.setStatus(StatusEnum.SUCCESS);
            return response;
        } catch (DataIntegrityViolationException e) {
            response = new ErrorResponse("User with this email already exists");
            return response;
        } catch (ConstraintViolationException e){
            response = new ErrorResponse("Email not allowed!");
            return response;
        }
    }

    @PostMapping("/login")
    public @ResponseBody Response login(@RequestBody User login){
        Response response;
        User user = userRepo.findByUsername(login.getUsername());
        if(user == null){
            return new ErrorResponse("Wrong username or password");
        }
        if(login.getPassword() == null || login.getPassword().isEmpty() || !login.getPassword().equals(user.getPassword())){
            return new ErrorResponse("Wrong username or password");
        }

        Token token = TokenFactory.getToken();
        token.setUser(user);
        tokenRepo.save(token);

        return new TokenResponse(token);
    }

    @PostMapping("/login/refresh")
    public @ResponseBody Response refresh(@RequestHeader("AuthorizationToken") String authorizationToken){
        if(!this.tokenSrv.validate(authorizationToken)){
            return new ErrorResponse("Invalid token");
        }
        Token token = tokenRepo.findByToken(authorizationToken);

        return new TokenResponse(this.tokenSrv.extendValability(token));

    }

    @PostMapping("/logout")
    public @ResponseBody Response logout(@RequestHeader("AuthorizationToken") String authorizationToken){
        if(!this.tokenSrv.validate(authorizationToken)){
            return new ErrorResponse("Invalid token");
        }
        Token token = tokenRepo.findByToken(authorizationToken);

        tokenRepo.delete(token);

        Response respone = new Response();
        respone.setStatus(StatusEnum.SUCCESS);
        return respone;
    }
}
