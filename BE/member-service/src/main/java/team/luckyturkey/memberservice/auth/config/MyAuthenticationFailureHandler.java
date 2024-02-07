package team.luckyturkey.memberservice.auth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException, IOException {
        // 인증 실패시 메인 페이지로 이동
        // todo : 어디로 이동할건지 url 맵핑
        response.sendRedirect("http://localhost:8080/");
    }

}
