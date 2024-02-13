package team.luckyturkey.memberservice.auth.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RefreshTokenService refreshTokenService;
    //내부에 미리 저장해둔 SecretKey 생성
    private final SecretKey secretKey;


    @Autowired
    public JWTUtil(RefreshTokenService refreshTokenService, @Value("${spring.jwt.secret}") String secret) {
        this.refreshTokenService = refreshTokenService;

        //객체변수로 암호화
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }


    public GeneratedToken generateToken(long memberId, String memberLoginId, String memberRole) {
        // refreshToken과 accessToken을 생성한다.

        String refreshToken = generateRefreshToken(memberId, memberLoginId, memberRole);
        String accessToken = generateAccessToken(memberId,memberLoginId, memberRole);

        // 토큰을 Redis에 저장한다.
        refreshTokenService.saveTokenInfo(memberLoginId, refreshToken, accessToken);
        return new GeneratedToken(accessToken, refreshToken);
    }

    //RefreshToken 생성
    public String generateRefreshToken(Long memberId, String memberLoginId, String memberRole) {
        // 토큰의 유효 기간을 밀리초 단위로 설정.
        long refreshPeriod = 1000L * 60L * 60L * 24L * 14; // 2주

        return Jwts.builder()
                .claim("memberId", memberId)
                .claim("memberLoginId", memberLoginId) //멤버 이름
                .claim("memberRole", memberRole) //멤버 권한
                // 발행일자를 넣는다.
                .issuedAt(new Date(System.currentTimeMillis()))
                // 토큰의 만료일시를 설정한다.
                .expiration(new Date(System.currentTimeMillis() + refreshPeriod))
                // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                .signWith(secretKey)
                .compact();
    }

    //AccessToken 생성
    public String generateAccessToken(Long memberId, String memberLoginId, String memberRole) {

        long tokenPeriod = 1000L * 60L * 10L; // 10분

        return Jwts.builder()
                .claim("memberId", memberId)
                .claim("memberLoginId", memberLoginId) //멤버 이름
                .claim("memberRole", memberRole) //멤버 권한
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenPeriod))
                .signWith(secretKey)
                .compact();
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

//    public String getMemberEmail(String token) {
//
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberEmail", String.class);
//    }

//    public String getMemberName(String token) {
//
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberName", String.class);
//    }
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Long getMemberId(String token) {
        return  Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberId", Long.class);
    }


}