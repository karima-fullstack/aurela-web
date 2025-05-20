package web.aurela.aurelaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.aurela.aurelaweb.Entities.Token;


import java.util.Optional;
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
