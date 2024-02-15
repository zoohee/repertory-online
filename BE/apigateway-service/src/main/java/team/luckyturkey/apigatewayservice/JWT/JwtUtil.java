package team.luckyturkey.apigatewayservice.JWT;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
public class JwtUtil {

    //내부에 미리 저장해둔 SecretKey 생성
    private final SecretKey secretKey;

    @Autowired
    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {

        //객체변수로 암호화
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }



    public boolean verifyToken(String token) {

        try {

            //토큰이 만료되었는지 체크
            return !isExpired(token);

        } catch(Exception e) {
            log.error(e.toString());
            return false;
        }

    }

    public String getMemberLoginId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberLoginId", String.class);
    }
    public String getMemberRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberRole", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Long getMemberId(String token) {
        return  Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberId", Long.class);
    }


}