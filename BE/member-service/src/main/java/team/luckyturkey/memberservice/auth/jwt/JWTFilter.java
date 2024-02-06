package team.luckyturkey.memberservice.auth.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import team.luckyturkey.memberservice.member.dto.CustomMemberDetails;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.member.repository.MemberRepository;

import java.io.IOException;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    public MemberRepository memberRepository;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //토큰검증 구현
        //request에서 Authorization 헤더를 찾음
        String atc= request.getHeader("Authorization");

//        //request Header에서 AccessToken  가져오기
//        String atc = request.getHeader("Authorization");

        // 토큰 검사 생략(모두 허용 URL의 경우 토큰 검사 통과)
        if (!StringUtils.hasText(atc)) {
            doFilter(request, response, filterChain);
            return;
        }



        //Authorization 헤더 검증
        if (atc == null || !atc.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = atc.split(" ")[1];

        // AccessToken을 검증하고, 만료되었을경우 예외를 발생시킨다.
        if (!jwtUtil.verifyToken(token)) {
            filterChain.doFilter(request, response);
            log.info("access token expired");
            return;
        }

        //토큰 소멸 시간 검증 //얘는 jwtUtil에서 함
//        if (jwtUtil.isExpired(token)) {
//
//            System.out.println("token expired");
//            filterChain.doFilter(request, response);
//
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }

        //토큰이 유효성 검사를 모두 마치고 여기까지 내려오면
        //토큰에서 memberName과 memberRole 획득
        String memberName = jwtUtil.getMemberName(token);
        String memberEmail = jwtUtil.getMemberEmail(token);
        String memberRole = jwtUtil.getMemberRole(token);
        log.info("memberName ={}", memberName);
        log.info("memberEmail = {}", memberEmail);
        log.info("memberRole ={}", memberRole);
        //member를 생성하여 값 set
        Member member = new Member();
        member.setMemberName(memberName);
        member.setMemberEmail(memberEmail);
        member.setMemberPassword("temppassword");
        member.setMemberRole(memberRole);

        log.info("member.memberName={}", member.getMemberName());
        log.info("member.memberEmail={}", member.getMemberEmail());
        log.info("member.memberRole ={}", member.getMemberRole());


        // AccessToken의 값이 있고, 유효한 경우에 진행한다.
        if (jwtUtil.verifyToken(atc)) {

            // AccessToken 내부의 payload에 있는 email로 user를 조회한다. 없다면 예외를 발생시킨다 -> 정상 케이스가 아님
            Member findMember = memberRepository.findByMemberEmail(jwtUtil.getMemberEmail(atc));


            //UserDetails에 회원 정보 객체 담기
            CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

            //스프링 시큐리티 인증 토큰 생성
            Authentication auth = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());


            // SecurityContext에 인증 객체를 등록해준다.
//            Authentication auth = getAuthentication(userDto);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }



        //스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());


//        //세션에 사용자 등록 //세션 안쓸거야
//        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
