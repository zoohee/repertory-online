package team.luckyturkey.memberservice.auth.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.memberservice.auth.jwt.JWTUtil;
import team.luckyturkey.memberservice.auth.jwt.RefreshToken;
import team.luckyturkey.memberservice.member.repository.RefreshTokenRepository;
import team.luckyturkey.memberservice.service.RefreshTokenService;

@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;


    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestHeader("Authorization") final String accessToken){
        String atc = accessToken.split(" ")[1];

        log.info("memberId = {}", jwtUtil.getMemberId(atc));

        if(jwtUtil.isExpired(atc)){
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }

        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<RefreshToken> getToken(String access){

        String atc = access.split(" ")[1];

        RefreshToken refreshToken;
        try{
            refreshToken = refreshTokenService.findToken(atc);
        }catch (Exception e){
            log.error("something wrong = {}", e.toString());
            throw e;
        }
        log.info("RefreshToken={}", refreshToken);

        if(refreshToken == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(refreshToken);

    }



    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") final String accessToken, @RequestHeader("Refresh") final String refreshToken) {

        String atc = accessToken.split(" ")[1];
        String rft = refreshToken.split(" ")[1];

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        RefreshToken refresh = refreshTokenService.findToken(atc);


        // RefreshToken이 존재한다면 실행
        if (rft != null) {
            //레디스
            String redisRefreshToken = refresh.getRefreshToken();

            // Refresh토큰 객체에서 refreshToken을 꺼내와서 헤더로 전달받은 값과 비교
            if(!redisRefreshToken.equals(rft)){ //다르면 접근 거부
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("잘못된 리프레시 토큰입니다.");
            }

            //리프레시토큰이 만료되었는지 확인
            if(!jwtUtil.verifyToken(redisRefreshToken)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("리프레시 토큰이 만료되었습니다.");
            }
            //두가지 검사를 다 통과하면

            log.info("나 여기까진 왔어요");
            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            Long memberId = jwtUtil.getMemberId(redisRefreshToken);
            String memberLoginId = jwtUtil.getMemberLoginId(redisRefreshToken);
            String memberRole = jwtUtil.getMemberRole(redisRefreshToken);

            String newAccessToken = jwtUtil.generateAccessToken(memberId, memberLoginId, memberRole);
            // 액세스 토큰의 값을 수정해준다.
            refreshTokenService.saveTokenInfo(memberLoginId, redisRefreshToken,newAccessToken);
            // 새로운 액세스 토큰을 반환해준다.
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + newAccessToken)
                    .body("새로운 액세스 토큰이 발급되었습니다.");
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("이미 로그아웃된 사용자입니다");
    }

}
