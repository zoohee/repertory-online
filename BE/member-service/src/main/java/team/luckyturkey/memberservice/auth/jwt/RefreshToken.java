package team.luckyturkey.memberservice.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 14) //redis 데이터의 유효시간
public class RefreshToken implements Serializable {

    @Id
    private String id;

    private String refreshToken;

    @Indexed //findByAccessToken 가능 //이게 있어야 필드값으로 데이터 찾아올 수 있음
    private String accessToken;


    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}