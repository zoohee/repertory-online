package team.luckyturkey.memberservice.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2Member implements OAuth2User {

    private final OAuth2Response oAuth2Response;
    private final String memberRole;

    public CustomOAuth2Member(OAuth2Response oAuth2Response, String memberRole){
        this.oAuth2Response = oAuth2Response;
        this.memberRole = memberRole;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberRole;
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getMeberName(){

        return oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    }
}
