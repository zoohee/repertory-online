package team.luckyturkey.communityservice.service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private SecretKey secretKey;
    private final TokenService tokenService;

    @Autowired
    public JwtUtil(@Value("${spring.jwt.secret}") String secret, TokenService tokenService) {

        //객체변수로 암호화
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.tokenService = tokenService;
    }

    public Long getMemberId(String token) {
        return  Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberId", Long.class);
    }
    public String getMemberLoginId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberLoginId", String.class);
    }
    public String getMemberRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberRole", String.class);
    }

    // Controller -> Request Header ("Authorization")
    public Long extractMemberIdFromToken(String accessToken) {
        String atc = accessToken.split(" ")[1];
        tokenService.setToken(atc);
        return getMemberId(atc);
    }
}
