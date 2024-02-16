package team.luckyturkey.memberservice.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.luckyturkey.memberservice.auth.jwt.JWTFilter;
import team.luckyturkey.memberservice.auth.jwt.JWTUtil;
import team.luckyturkey.memberservice.auth.jwt.JwtExceptionFilter;
import team.luckyturkey.memberservice.auth.jwt.LoginFilter;
import team.luckyturkey.memberservice.service.CustomOAuth2MemberService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyAuthenticationSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final JWTUtil jwtUtil;
    private final JWTFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final MyAuthenticationFailureHandler oAuth2LoginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic((auth) -> auth.disable());

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //세션 stateless 상태로 설정
        http
                //세션이 있어도 사용하지 않고, 없어도 만들지 않음
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .oauth2Login((oauth2) -> oauth2
                        .failureHandler(oAuth2LoginFailureHandler)
                        .successHandler(oAuth2LoginSuccessHandler)
                        // 로그인 페이지 경로를 재 매핑
                        .loginPage("/login/OAuth")
//                        // 등록한 유저 디테일 서비스를 사용하기 위한 엔드포인트 설정
//                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository()) // 커스텀한 OAuth 클라이언트 등록 정보 사용
//                        // OAuth 2.0 로그인 성공 후 인가된 클라이언트 정보를 저장하고 조회하기 위한 서비스 설정
//                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                        // 사용자 정보 엔드포인트 설정, 커스텀한 OAuth2MemberService를 사용하여 사용자 정보를 가져옴
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2MemberService)));

        //인가작업
       http
                .authorizeHttpRequests((auth) -> auth
                        // 모든 경로("/", "/login", "/join", "/member/**", "/swagger-ui/**", "/v3/api-docs/**", "/oauth2/**")에 대한 접근 허용
                        .requestMatchers("/**","/login","/","/join", "/member/**", "/swagger-ui/**", "/v3/api-docs/**","/oauth2/**").permitAll()
                        // "/admin" 경로에 대한 접근은 "ADMIN" 역할을 가진 사용자만 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // 그 외의 모든 요청은 인증된 사용자에게만 허용
                        .anyRequest().authenticated());


        //JWTFilter 등록
        http
                //로그인 필터 이전에 JWT필터가 작동함
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
                .addFilterBefore(jwtExceptionFilter, JWTFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { //password를 암호화해서 전송

        return new BCryptPasswordEncoder();
    }
}