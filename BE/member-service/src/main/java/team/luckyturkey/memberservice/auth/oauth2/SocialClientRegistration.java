package team.luckyturkey.memberservice.auth.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

@Component
public class SocialClientRegistration {

    public ClientRegistration googleClientRegistration() {

        //http://localhost:8080/member/oauth2/authorization/google 구글 로그인 페이지 url
        return ClientRegistration.withRegistrationId("google")
                .clientId("369405807431-pki0r440oh137ckh7lmotdof8sat2eot.apps.googleusercontent.com")
                .clientSecret("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")
//                .redirectUri("http://localhost:8080/member/success") //redirect url 수정해야함 //여기로 접근하면 구글로그인이 돼
                .redirectUri("https://repertory.online:8000/member/success")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token") //토큰 발급 uri
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .issuerUri("https://accounts.google.com")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();
    }

//    public ClientRegistration naverClientRegistration() {
//
//        return ClientRegistration.withRegistrationId("naver")
//                .clientId("아이디")
//                .clientSecret("비밀번호")
//                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("name", "email")
//                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
//                .tokenUri("https://nid.naver.com/oauth2.0/token")
//                .userInfoUri("https://openapi.naver.com/v1/nid/me")
//                .userNameAttributeName("response")
//                .build();
//    }
}
