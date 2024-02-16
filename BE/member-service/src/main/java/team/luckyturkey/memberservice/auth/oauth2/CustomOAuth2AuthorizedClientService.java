package team.luckyturkey.memberservice.auth.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class CustomOAuth2AuthorizedClientService {

    // JdbcOAuth2AuthorizedClientService 빈을 생성하는 메서드
    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcTemplate jdbcTemplate, ClientRegistrationRepository clientRegistrationRepository){

        // JdbcOAuth2AuthorizedClientService 인스턴스를 생성하고 JdbcTemplate 및 ClientRegistrationRepository를 전달하여 초기화
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }
}
