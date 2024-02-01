package team.luckyturkey.memberservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.dto.CustomOAuth2Member;
import team.luckyturkey.memberservice.dto.GoogleResponse;
import team.luckyturkey.memberservice.dto.NaverResponse;
import team.luckyturkey.memberservice.dto.OAuth2Response;

@Slf4j
@Service
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User = {}", oAuth2User.getAttributes());

        //provider를 구분해야함
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;


        if(registrationId.equals("naver")){

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        }else if(registrationId.equals(("google"))){

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        }else{
            return null;
        }

        String memberRole = "ROLE_USER";
        return new CustomOAuth2Member(oAuth2Response, memberRole);

    }
}
