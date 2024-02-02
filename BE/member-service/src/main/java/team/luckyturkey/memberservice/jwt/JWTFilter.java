package team.luckyturkey.memberservice.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import team.luckyturkey.memberservice.dto.CustomMemberDetails;
import team.luckyturkey.memberservice.entity.Member;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //토큰검증 구현
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //토큰에서 memberName과 memberRole 획득
        String memberName = jwtUtil.getMemberName(token);
        String memberRole = jwtUtil.getMemberRole(token);
        log.info("memberName ={}", memberName);
        log.info("memberRole ={}", memberRole);
        //member를 생성하여 값 set
        Member member = new Member();
        member.setMemberName(memberName);
        member.setMemberPassword("temppassword");
        member.setMemberRole(memberRole);

        log.info("member.memberName={}", member.getMemberName());
        log.info("member.memberRole ={}", member.getMemberRole());
        //UserDetails에 회원 정보 객체 담기
        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
