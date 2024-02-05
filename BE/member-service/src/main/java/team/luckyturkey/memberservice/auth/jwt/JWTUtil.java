package team.luckyturkey.memberservice.auth.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
//@RequiredArgsConstructor
public class JWTUtil {

//    private final JwtProperties jwtProperties;
//    private final RefreshTokenService tokenService;
    //내부에 미리 저장해둔 SecretKey 생성
    private final SecretKey secretKey;



    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {

        //객체변수로 암호화
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }




    //check
    public String getMemberName(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberName", String.class);
    }

    public String getMemberRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberRole", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }


    //Create Token
    public String createJwt(String memberName, String memberRole, Long expiredMs) {

        return Jwts.builder()
                .claim("memberName", memberName)
                .claim("memberRole", memberRole)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}