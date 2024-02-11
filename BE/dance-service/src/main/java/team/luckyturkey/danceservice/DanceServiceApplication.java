package team.luckyturkey.danceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DanceServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DanceServiceApplication.class, args);
	}

}
