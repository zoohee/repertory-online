package team.luckyturkey.memberservice.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import team.luckyturkey.memberservice.auth.jwt.JWTUtil;
import team.luckyturkey.memberservice.auth.jwt.RefreshToken;
import team.luckyturkey.memberservice.service.RefreshTokenService;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //헤더에서 토큰 가져오기
        String token = request.getHeader("Authorization");
        String atc = token.split(" ")[1];
        //헤더에 토큰이 있다면
        if(atc != null){
            //토큰 검증
            if(!jwtUtil.verifyToken(atc)){ //토큰 검증 실패라면
                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                return;
            }
            try {
                // access token을 이용해서 redis에서 토큰 검색
                RefreshToken foundTokenInfo = refreshTokenService.findToken(atc);
                // redis에 검색된 토큰이 없다면
                if(foundTokenInfo == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 에러 코드 설정
                    return;
                }
                //토큰이 있으면 검색된 토큰 삭제
                refreshTokenService.deleteRefreshToken(foundTokenInfo.getId());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 에러 코드 설정
                return;
            }
//
//            // access token을 이용해서 redis에서 토큰 검색
//            RefreshToken foundTokenInfo = refreshTokenService.findToken(atc);
//            // redis에 검색된 토큰이 없다면
//            if(foundTokenInfo == null) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 에러 코드 설정
//                return;
//            }
//            //토큰이 있으면 검색된 토큰 삭제
//            refreshTokenService.deleteRefreshToken(foundTokenInfo.getId());

        }else{
            //토큰이 없으면...?
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 에러 코드 설정
        }

    }
}
