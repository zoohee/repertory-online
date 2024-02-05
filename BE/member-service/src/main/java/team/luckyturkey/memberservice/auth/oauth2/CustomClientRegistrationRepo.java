package team.luckyturkey.memberservice.auth.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class CustomClientRegistrationRepo {

    // SocialClientRegistration 타입의 의존성 주입
    private final SocialClientRegistration socialClientRegistration;

    // 생성자에서 SocialClientRegistration을 주입받아 멤버 변수에 저장
    public CustomClientRegistrationRepo(SocialClientRegistration socialClientRegistration){
        this.socialClientRegistration = socialClientRegistration;
    }

    // ClientRegistrationRepository를 반환하는 메서드
    public ClientRegistrationRepository clientRegistrationRepository(){

        // InMemoryClientRegistrationRepository를 생성하고, 구글 클라이언트 등록 정보를 인메모리에 저장
        return new InMemoryClientRegistrationRepository(socialClientRegistration.googleClientRegistration());
    }
}

