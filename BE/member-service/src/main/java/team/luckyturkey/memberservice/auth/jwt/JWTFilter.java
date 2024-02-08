package team.luckyturkey.memberservice.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
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
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import team.luckyturkey.memberservice.member.dto.CustomMemberDetails;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.member.repository.MemberRepository;
import team.luckyturkey.memberservice.member.repository.RefreshTokenRepository;
import team.luckyturkey.memberservice.service.RefreshTokenService;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    public MemberRepository memberRepository;

    public RefreshTokenRepository refreshTokenRepository;
    public RefreshTokenService refreshTokenService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //토큰검증 구현
        //request에서 Authorization 헤더를 찾음
        String atc= request.getHeader("Authorization");


        // 토큰 검사 생략(모두 허용 URL의 경우 토큰 검사 통과)
        // 권한이 없다면 접근 거부될 것
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
            log.info("access token expired");

            //만료된 경우
            //redis에 저장된 정보를 만료된 accesstoken으로 찾아온다
            //없으면 예외발생
            //todo : 얘가 못찾아옴
            RefreshToken foundTokenInfo = refreshTokenRepository.findByAccessToken(token)
                            .orElseThrow(() -> new NoSuchElementException("RefreshToken을 찾을 수 없습니다."));

            //있으면 refreshToken 저장
            String refreshToken = foundTokenInfo.getRefreshToken();

            //refreshToken도 만료되었으면
            if(jwtUtil.isExpired(refreshToken)){
                throw new ExpiredJwtException(null,null,"RefreshToken이 만료되었습니다");
            }

            //refreshToken이 유효하면 멤버 아이디(식별자) 가져오기
            String Id = foundTokenInfo.getId();

            //Id로 사용자를 찾는다.
            Member exist = memberRepository.findByMemberLoginId(Id);

            if(exist == null){
                throw new NoSuchElementException("토큰을 발급할 멤버가 존재하지 않습니다");
            }

            //찾아온 사용자 정보로 다시 access토큰을 발급하고,
            String newToken = jwtUtil.generateAccessToken(exist.getMemberLoginId(), exist.getMemberRole());

            //refresh token과 함께 Redis에 업데이트해준다.
            refreshTokenService.saveTokenInfo(exist.getMemberLoginId(), newToken, refreshToken);

            //todo : 클라이언트측도 업데이트해준다







            filterChain.doFilter(request, response);

            return;
        }


        //토큰이 유효성 검사를 모두 마치고 여기까지 내려오면
        //토큰에서 memberName과 memberRole 획득
        String memberLoginId = jwtUtil.getMemberLoginId(token);
        String memberRole = jwtUtil.getMemberRole(token);
        log.info("memberLoginId ={}", memberLoginId);
        log.info("memberRole ={}", memberRole);
        //member를 생성하여 값 set
        Member member = new Member();
        member.setMemberLoginId(memberLoginId);
        member.setMemberPassword("temppassword");
        member.setMemberRole(memberRole);

        log.info("member.memberLoginId={}", member.getMemberLoginId());
        log.info("member.memberRole ={}", member.getMemberRole());


        // AccessToken의 값이 있고, 유효한 경우에 진행한다.
        if (jwtUtil.verifyToken(atc)) {

            // AccessToken 내부의 payload에 있는 email로 user를 조회한다. 없다면 예외를 발생시킨다 -> 정상 케이스가 아님
            Member findMember = memberRepository.findByMemberLoginId(jwtUtil.getMemberLoginId(atc));


            //UserDetails에 회원 정보 객체 담기
            CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

            //스프링 시큐리티 인증 토큰 생성
            Authentication auth = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());


            // SecurityContext에 인증 객체를 등록해준다.
//            Authentication auth = getAuthentication(userDto);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        }



        //스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());




    }
}
