package agageonea.cloudstorage.filters;

import agageonea.cloudstorage.domain.authentication.Token;
import agageonea.cloudstorage.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthorizationTokenFilter extends GenericFilterBean {

    @Autowired
    TokenRepository tokenRepo;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authorizationToken = request.getHeader("AuthorizationToken");
        if(authorizationToken == null || authorizationToken.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "AuthorizationToken missing from headers");
            return;
        }
        Token token = tokenRepo.findByToken(authorizationToken);
        if(token == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        Date expiresAt = token.getExpiresAt();
        Date now = new Date();
        if(now.after(expiresAt)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

}
