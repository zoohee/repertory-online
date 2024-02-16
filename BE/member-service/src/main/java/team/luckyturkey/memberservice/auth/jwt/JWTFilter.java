package team.luckyturkey.memberservice.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import team.luckyturkey.memberservice.member.dto.CustomMemberDetails;
import team.luckyturkey.memberservice.member.entity.Member;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //토큰검증 구현
        //request에서 Authorization 헤더를 찾음
        String atc= request.getHeader("Authorization");
        log.info("atc is {}", atc);

        // 토큰 검사 생략(모두 허용 URL의 경우 토큰 검사 통과)
        // 권한이 없다면 접근 거부될 것
        if (!StringUtils.hasText(atc)) {
            log.info("Authorization head is blank");
            filterChain.doFilter(request, response);
            return;
        }


        //Authorization 헤더 검증 Bearer가 붙어서 왔는지
        if (!atc.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        log.info("atc = {} ", atc);
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = atc.split(" ")[1];

        log.info("token = {} ", token);

        // AccessToken을 검증하고, 만료되었을경우 예외를 발생시킨다.
        if (!jwtUtil.verifyToken(token)) {
            log.info("access token expired");

            if(request.getHeader("Refresh") != null){

                filterChain.doFilter(request, response);
                return;
            }

            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            return;

        }


        //토큰이 유효성 검사를 모두 마치고 여기까지 내려오면
        //토큰에서 memberName과 memberRole 획득
        String memberLoginId = jwtUtil.getMemberLoginId(token);
        String memberRole = jwtUtil.getMemberRole(token);

        //member를 생성하여 값 set
        Member member = new Member();
        member.setMemberLoginId(memberLoginId);
        member.setMemberPassword("temppassword");
        member.setMemberRole(memberRole);

        log.info("member.memberLoginId={}", member.getMemberLoginId());
        log.info("member.memberRole ={}", member.getMemberRole());


//        // AccessToken 내부의 payload에 있는 LoginId로 user를 조회한다. 없다면 예외를 발생시킨다 -> 정상 케이스가 아님
//        Member findMember = memberRepository.findByMemberLoginId(jwtUtil.getMemberLoginId(atc));


        //UserDetails에 회원 정보 객체 담기
        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        //스프링 시큐리티 인증 토큰 생성
        Authentication auth = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());


        // SecurityContext에 인증 객체를 등록해준다.
//            Authentication auth = getAuthentication(userDto);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);




        //스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());




    }
}
