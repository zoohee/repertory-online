package team.luckyturkey.memberservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.Status.MemberAuthorityStatus;
import team.luckyturkey.memberservice.auth.dto.OAuth2Attribute;
import team.luckyturkey.memberservice.member.dto.responsedto.GoogleResponse;
import team.luckyturkey.memberservice.member.dto.responsedto.NaverResponse;
import team.luckyturkey.memberservice.auth.dto.OAuth2Response;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.member.repository.OAuthMeberRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final OAuthMeberRepository oAuthMeberRepository;


    public CustomOAuth2MemberService (OAuthMeberRepository oAuthMeberRepository){
        this.oAuthMeberRepository = oAuthMeberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        //기본 OAuth2UserService 객체 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        log.info("OAuth2User = {}", oAuth2User.getAttributes());

        //클라이언트 등록 ID(google)와 사용자 이름 속성을 가져온다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 사용하여 가져온 OAuth2User 정보로 OAuth2Attribute 객체를 만든다.
        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // OAuth2Attribute의 속성값들을 Map으로 반환 받는다.
        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();


        //OAuth2Response 의 빈 객체를 생성한다
        OAuth2Response oAuth2Response = null;

        //registrationId에 따라서 맞는 response를 되돌려준다
        if(registrationId.equals("naver")){

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        }else if(registrationId.equals(("google"))){

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        }else{
            return null;
        }

        //id만들어서 이미 회원가입한 회원인지 체크
        //로그인 아이디 가져오기
        String memberLoginId = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        //email을 통해 이미 가입한 회원인지 체크
        //사용자 email 정보를 가져온다
        String memberEmail = oAuth2Response.getEmail();
//        Member existData = oAuthMeberRepository.findByMemberLoginId(memberLoginId);
        Optional<Member> existData = Optional.ofNullable(oAuthMeberRepository.findByMemberEmail(memberEmail));



        String memberRole = null;
//        if(existData == null){ //처음 로그인 한 경우

        if(existData.isEmpty()) {

            //존재하지 않으면 현재 가진 정보를 받아서 회원가입 로직을 진행한다.
            ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

            //회원이 존재하지 않으면 memberAttribute의 exist값을 false로 넣어준다.
            memberAttribute.put("exist", false);

            memberAttribute.put("memberLoginId", memberLoginId);
            memberAttribute.put("memberEmail", oAuth2Response.getEmail());
            memberAttribute.put("memberProfile",oAuth2Response.getPicture());
            memberAttribute.put("memberJoinDate", String.valueOf(currentDateTime));

            log.info("memberAttribute ={}", memberAttribute);

            Member member = new Member();



            member.setMemberName(oAuth2Response.getName()); // 추가: 이름 설정
            log.info("member = {}", member);
            member.setMemberLoginId(memberLoginId);
            member.setMemberEmail(oAuth2Response.getEmail());
            member.setMemberRole(MemberAuthorityStatus.ROLE_SOCIAL_LOGIN_MEMBER.getAuthority());
            member.setMemberProfile(oAuth2Response.getPicture());
            member.setMemberJoinDate(String.valueOf(currentDateTime)); // 수정: 가입 날짜 설정

            log.info("member = {}", member);
            //멤버 정보를 db에 저장
            oAuthMeberRepository.save(member);
            memberRole = MemberAuthorityStatus.ROLE_SOCIAL_LOGIN_MEMBER.getAuthority();

            return new DefaultOAuth2User(
                    // 회원의 권한(회원이 존재하지 않으므로 기본권한인 ROLE_USER를 넣어준다)
                    // 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    memberAttribute, "email");



//        }else{ //원래 있던 멤버인 경우
        }

            //멤버가 있으면
            memberAttribute.put("exist", true);



            //멤버 롤 가져오기
            memberRole = existData.get().getMemberRole();

            if (existData.get().getMemberJoinDate() == null) {
                existData.get().setMemberJoinDate(String.valueOf(ZonedDateTime.now(ZoneId.of("서버_시간대"))));
            }
            // 새로 받은 데이터로 업데이트
            existData.get().setMemberEmail(oAuth2Response.getEmail());
            existData.get().setMemberProfile(oAuth2Response.getPicture());

            // 데이터베이스에 업데이트
            oAuthMeberRepository.save(existData.get());

            log.info("login success!!!!!!!!!!!!!!!!!!!!!");


        //회원의 권한과 회원속성, 속성이름을 사용해 DefaultOAuth2User 객체를 생성해 반환
            return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE".concat(existData.get().getMemberRole()))),
                memberAttribute, "email");


        }

//        return new CustomOAuth2Member(oAuth2Response, memberRole);

    }

