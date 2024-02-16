package team.luckyturkey.memberservice.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import team.luckyturkey.memberservice.auth.jwt.RedisProperties;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;

    // RedisConnectionFactory Bean을 생성하는 메서드
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 연결 구성을 위한 RedisStandaloneConfiguration 생성
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // Redis 호스트, 포트 및 암호 설정
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        // LettuceConnectionFactory를 사용하여 RedisConnectionFactory 생성 및 반환

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // RedisTemplate Bean을 생성하는 메서드
    @Bean
    public RedisTemplate<byte[], byte[]> redisTemplate() {
        // RedisTemplate 생성
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        // Redis 연결 설정을 사용하여 RedisTemplate에 연결 설정
        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setEnableTransactionSupport(true);
        // RedisTemplate 반환
        return redisTemplate;
    }

}