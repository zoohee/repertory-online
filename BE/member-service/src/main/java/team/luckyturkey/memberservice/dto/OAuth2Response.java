package team.luckyturkey.memberservice.dto;

public interface OAuth2Response {

    //google? kakao?
    String getProvider();

    String getProviderId();

    String getEmail();

    //사용자 실명
    String getName();
}
