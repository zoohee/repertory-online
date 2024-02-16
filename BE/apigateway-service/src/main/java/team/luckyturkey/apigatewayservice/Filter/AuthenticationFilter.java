package team.luckyturkey.apigatewayservice.Filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import team.luckyturkey.apigatewayservice.JWT.JwtUtil;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

                if (exchange.getRequest().getPath().toString().equals("/member/login")) {
                    return chain.filter(exchange);
                }

                if (exchange.getRequest().getPath().toString().equals("/member/join")) {
                    return chain.filter(exchange);
                }

                if (exchange.getRequest().getPath().toString().equals("/member/id-validation")) {
                    return chain.filter(exchange);
                }


            // 헤더에 토큰이 있는지 확인
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    // 인증 헤더가 없을 때 예외 발생
                    throw new RuntimeException("인증 헤더가 없습니다");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                //헤더가 있고 Bearer 접두사로 시작하면
                if (authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7); //토큰 부분만 꺼내기
                }else{
                    throw new RuntimeException("잘못된 형식의 토큰입니다");
                }

                if(!jwtUtil.getMemberRole(authHeader).equals("ROLE_REGISTERED_MEMBER")){
                    log.info(jwtUtil.getMemberRole(authHeader));
                    try {
                        throw new AuthenticationException("권한이 없는 사용자입니다");
                    } catch (AuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    // 토큰 유효성 검사
                    if (!jwtUtil.verifyToken(authHeader)) {
                        if (!exchange.getRequest().getHeaders().containsKey("Refresh")) {
                            // 클라이언트에게 "Expectation Failed" 상태 코드를 보냄
                            exchange.getResponse().setStatusCode(HttpStatus.EXPECTATION_FAILED);
                            return exchange.getResponse().setComplete();
                        } else {
                            String refreshHeader = exchange.getRequest().getHeaders().get("Refresh").get(0);
                            // Refresh 헤더의 토큰이 만료되었는지 확인
                            if (!jwtUtil.verifyToken(refreshHeader)) {
                                // 요청이 "/token/refresh"인 경우 필터를 통과
                                if (exchange.getRequest().getPath().toString().equals("/token/refresh")) {
                                    return chain.filter(exchange);
                                }
                            } else {
                                // Refresh 토큰도 만료되면 로그아웃된 사용자라는 예외와 함께 다른 적절한 응답을 보냄
                                throw new RuntimeException("로그아웃된 사용자입니다.");
                            }
                            // 토큰이 만료되었을 경우 예외 발생
                            throw new RuntimeException("만료된 토큰입니다");
                        }
                    }

                } catch (Exception e) {
                    // 유효하지 않은 접근 시 예외 발생
//                    System.out.println("유효하지 않은 접근입니다...!");
                    throw new RuntimeException("애플리케이션에 대한 무단 접근입니다");
                }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }


}
