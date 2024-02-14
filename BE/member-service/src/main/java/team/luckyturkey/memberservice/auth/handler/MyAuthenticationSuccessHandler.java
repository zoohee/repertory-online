package team.luckyturkey.memberservice.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import team.luckyturkey.memberservice.auth.jwt.JWTUtil;
import team.luckyturkey.memberservice.auth.jwt.RefreshToken;
import team.luckyturkey.memberservice.member.dto.GeneratedToken;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.service.MemberService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // OAuth2User로 캐스팅하여 인증된 사용자 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        // 사용자 이메일을 가져온다.
        String memberEmail = oAuth2User.getAttribute("email");
        // 서비스 제공 플랫폼(GOOGLE, KAKAO, NAVER)이 어디인지 가져온다.
        String provider = oAuth2User.getAttribute("provider");
        String memberName = oAuth2User.getAttribute("name");


        log.info("provider = {}", provider);

        // CustomOAuth2UserService에서 셋팅한 로그인한 회원 존재 여부를 가져온다.
        boolean isExist = Boolean.TRUE.equals(oAuth2User.getAttribute("exist"));
        // OAuth2User로 부터 Role을 얻어온다.
        String memberRole = oAuth2User.getAuthorities().stream().
                findFirst() // 첫번째 Role을 찾아온다.
                .orElseThrow(IllegalAccessError::new) // 존재하지 않을 시 예외를 던진다.
                .getAuthority(); // Role을 가져온다.


        // 회원이 존재할경우
//        if (isExist) {

            Member Exist = memberService.getMemberByEmail(memberEmail);
            // 회원이 존재하면 jwt token 발행을 시작한다.
            long memberId = Exist.getId();
            GeneratedToken token = jwtUtil.generateToken(memberId, memberEmail, memberRole);
            log.info("jwtToken = {}", token);
            String accessToken = token.getAccessToken();
            String refreshToken = token.getRefreshToken();

            response.addHeader("Authorization", "Bearer " + accessToken);
            response.addHeader("Refresh", "Bearer " + refreshToken);

            response.setStatus(HttpServletResponse.SC_OK);
            //리다이렉트 ㄴㄴ
//            // accessToken을 쿼리스트링에 담는 url을 만들어준다.
//            String targetUrl = UriComponentsBuilder.fromUriString("http://repertory.online")
//                    .queryParam("accessToken", accessToken)
//                    .build()
//                    .encode(StandardCharsets.UTF_8)
//                    .toUriString();
//            log.info("redirect 준비");
//            // 로그인 확인 페이지로 리다이렉트 시킨다.
//            getRedirectStrategy().sendRedirect(request, response, targetUrl);


//        } else {
//            throw new IllegalArgumentException("비정상적 소셜로그인");
//            //지금은 로그인과 동시에 회원가입을 한다. 그래서 여기에 도달하지 않아.
//            //만약 커스텀 회원가입 창이 존재한다면, 여기서 새로운 리다이렉트요청을 보내서 나오는 페이지에서 회원가입을 해야한다.
//            //
//            // 회원이 존재하지 않을경우, 서비스 제공자와 email을 쿼리스트링으로 전달하는 url을 만들어준다.
////            String targetUrl = UriComponentsBuilder.fromUriString("http://3.39.72.204/loginSuccess")
////                    .queryParam("email", (String) oAuth2User.getAttribute("email"))
////                    .queryParam("provider", provider)
////                    .build()
////                    .encode(StandardCharsets.UTF_8)
////                    .toUriString();
////            // 회원가입 페이지로 리다이렉트 시킨다.
////            getRedirectStrategy().sendRedirect(request, response, targetUrl);
//        }
    }
}
