package team.luckyturkey.memberservice.member.dto.responsedto;

import team.luckyturkey.memberservice.auth.dto.OAuth2Response;

import java.util.Map;

public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute){

        this.attribute = (Map<String, Object>) attribute.get("response");


    };




    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("Id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getPicture() {
        return attribute.get("picture").toString();
    }
}
