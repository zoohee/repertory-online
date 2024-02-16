package team.luckyturkey.communityservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.luckyturkey.communityservice.service.TokenService;
import team.luckyturkey.communityservice.util.FeignClientInterceptor;

@Configuration
@EnableFeignClients(basePackages = {"team.luckyturkey.communityservice"})
public class OpenFeignConfig {

    @Autowired
    private TokenService tokenService;

    @Bean
    public FeignClientInterceptor feignInterceptor() {
        return new FeignClientInterceptor(tokenService);
    }
}