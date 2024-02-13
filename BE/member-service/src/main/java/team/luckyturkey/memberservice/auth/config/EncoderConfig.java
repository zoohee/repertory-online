package team.luckyturkey.memberservice.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { //password를 암호화해서 전송

        return new BCryptPasswordEncoder();
    }
}
