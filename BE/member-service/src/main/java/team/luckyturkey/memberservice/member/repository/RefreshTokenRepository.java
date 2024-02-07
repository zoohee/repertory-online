package team.luckyturkey.memberservice.member.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import team.luckyturkey.memberservice.auth.jwt.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    // accessToken으로 RefreshToken을 찾아온다.
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
