package team.luckyturkey.danceservice.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"team.luckyturkey.danceservice"})
public class OpenFeignConfig{

}