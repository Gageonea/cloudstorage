package agageonea.cloudstorage.repositories;

import agageonea.cloudstorage.domain.authentication.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}
