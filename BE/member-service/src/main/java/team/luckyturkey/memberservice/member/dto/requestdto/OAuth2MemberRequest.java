package team.luckyturkey.memberservice.member.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Map;

@Getter
@Setter
public class OAuth2MemberRequest {

    private final ClientRegistration clientRegistration;

    private final OAuth2AccessToken accessToken;

    private final Map<String, Object> additionalParameters;


    public OAuth2MemberRequest(ClientRegistration clientRegistration, OAuth2AccessToken accessToken, Map<String, Object> additionalParameters) {
        this.clientRegistration = clientRegistration;
        this.accessToken = accessToken;
        this.additionalParameters = additionalParameters;
    }
}
