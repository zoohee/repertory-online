package team.luckyturkey.memberservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.Status.MemberAuthorityStatus;
import team.luckyturkey.memberservice.dto.CustomOAuth2Member;
import team.luckyturkey.memberservice.dto.requestdto.OAtuh2LoginRequestDto;
import team.luckyturkey.memberservice.dto.responsedto.GoogleResponse;
import team.luckyturkey.memberservice.dto.responsedto.NaverResponse;
import team.luckyturkey.memberservice.dto.OAuth2Response;
import team.luckyturkey.memberservice.entity.Member;
import team.luckyturkey.memberservice.repository.OAuthMeberRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final OAuthMeberRepository oAuthMeberRepository;

    public CustomOAuth2MemberService (OAuthMeberRepository oAuthMeberRepository){
        this.oAuthMeberRepository = oAuthMeberRepository;
    }

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

        //id만들어서 이미 회원가입한 회원인지 체크
        //todo : 적절한 아이디 가져오는 로직으로 수정
        String memberLoginId = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        Member existData = oAuthMeberRepository.findByMemberLoginId(memberLoginId);


        String memberRole = null;
        if(existData == null){ //처음 로그인 한 경우


            Member member = new Member();

            ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

            member.setMemberName(oAuth2Response.getName()); // 추가: 이름 설정
            log.info("member = {}", member);
            member.setMemberLoginId(memberLoginId);
            member.setMemberEmail(oAuth2Response.getEmail());
            member.setMemberRole(MemberAuthorityStatus.ROLE_SOCIAL_LOGIN_MEMBER.getAuthority());
            member.setMemberProfile(oAuth2Response.getPicture());
            member.setMemberJoinDate(String.valueOf(currentDateTime)); // 수정: 가입 날짜 설정

            log.info("member = {}", member);
            //insert query
            oAuthMeberRepository.save(member);
            log.info("hello world");
            memberRole = MemberAuthorityStatus.ROLE_SOCIAL_LOGIN_MEMBER.getAuthority();
        }else{ //원래 있던 멤버인 경우
            //멤버 롤 가져오기
            memberRole = existData.getMemberRole();

            if (existData.getMemberJoinDate() == null) {
                existData.setMemberJoinDate(String.valueOf(ZonedDateTime.now(ZoneId.of("서버_시간대"))));
            }
            // 새로 받은 데이터로 업데이트
            existData.setMemberEmail(oAuth2Response.getEmail());
            existData.setMemberProfile(oAuth2Response.getPicture());

            oAuthMeberRepository.save(existData);

            log.info("login success!!!!!!!!!!!!!!!!!!!!!");


        }

        return new CustomOAuth2Member(oAuth2Response, memberRole);

    }
}
