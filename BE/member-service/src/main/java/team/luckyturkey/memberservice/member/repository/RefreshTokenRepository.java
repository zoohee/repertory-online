package team.luckyturkey.memberservice.member.repository;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    // accessToken으로 RefreshToken을 찾아온다.
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
