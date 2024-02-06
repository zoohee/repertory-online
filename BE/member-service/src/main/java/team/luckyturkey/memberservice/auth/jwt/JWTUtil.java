package team.luckyturkey.memberservice.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.member.dto.GeneratedToken;
import team.luckyturkey.memberservice.service.RefreshTokenService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
public class JWTUtil {

    private final RefreshTokenService tokenService;
    //내부에 미리 저장해둔 SecretKey 생성
    private final SecretKey secretKey;



    public JWTUtil(RefreshTokenService tokenService, @Value("${spring.jwt.secret}") String secret) {
        this.tokenService = tokenService;

        //객체변수로 암호화
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public GeneratedToken generateToken(String memberEmail, String memberRole) {
        // refreshToken과 accessToken을 생성한다.
        String refreshToken = generateRefreshToken(memberEmail, memberRole);
        String accessToken = generateAccessToken(memberEmail, memberRole);

        // 토큰을 Redis에 저장한다.
//        tokenService.saveTokenInfo(memberEmail, refreshToken, accessToken);
        return new GeneratedToken(accessToken, refreshToken);
    }

    public String generateRefreshToken(String memberEmail, String memberRole) {
        // 토큰의 유효 기간을 밀리초 단위로 설정.
        long refreshPeriod = 1000L * 60L * 60L * 24L * 14; // 2주

        return Jwts.builder()
                .claim("memberEmail", memberEmail) //멤버 이름
                .claim("memberRole", memberRole) //멤버 권한
                // 발행일자를 넣는다.
                .issuedAt(new Date(System.currentTimeMillis()))
                // 토큰의 만료일시를 설정한다.
                .expiration(new Date(System.currentTimeMillis() + refreshPeriod))
                // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                .signWith(secretKey)
                .compact();
    }

    //Create Token
    public String generateAccessToken(String memberEmail, String memberRole) {

        long tokenPeriod = 1000L * 60L * 30L; // 30분

        return Jwts.builder()
                .claim("memberEmail", memberEmail) //멤버 이름
                .claim("memberRole", memberRole) //멤버 권한
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenPeriod))
                .signWith(secretKey)
                .compact();
    }


    public boolean verifyToken(String token) {

        try {

            return isExpired(token);

        } catch(Exception e) {

            return false;
        }

//        try {
//            Jws<Claims> claims = Jwts.parser()
//                    .setSigningKey(secretKey) // 비밀키를 설정하여 파싱한다.
//                    .parseClaimsJws(token);  // 주어진 토큰을 파싱하여 Claims 객체를 얻는다.
//            // 토큰의 만료 시간과 현재 시간비교
//            return claims.getBody()
//                    .getExpiration()
//                    .after(new Date());  // 만료 시간이 현재 시간 이후인지 확인하여 유효성 검사 결과를 반환
//        } catch (Exception e) {
//            return false;
//        }
    }


    //check
    public String getMemberName(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberName", String.class);
    }

    public String getMemberRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberRole", String.class);
    }

    public String getMemberEmail(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberEmail", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }



}