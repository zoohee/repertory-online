package team.luckyturkey.danceservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.luckyturkey.danceservice.service.TokenService;
import team.luckyturkey.danceservice.util.FeignClientInterceptor;

@Configuration
@EnableFeignClients(basePackages = {"team.luckyturkey.danceservice"})
public class OpenFeignConfig {
    @Autowired
    private TokenService tokenService;

    @Bean
    public FeignClientInterceptor feignInterceptor() {
        return new FeignClientInterceptor(tokenService);
    }
}