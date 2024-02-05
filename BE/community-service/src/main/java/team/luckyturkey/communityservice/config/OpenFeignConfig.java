package team.luckyturkey.communityservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"team.luckyturkey.communityservice"})
public class OpenFeignConfig {

}