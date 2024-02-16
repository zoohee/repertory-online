package team.luckyturkey.memberservice.member.dto;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.luckyturkey.memberservice.member.entity.Member;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@ToString
public class CustomMemberDetails implements UserDetails {

    private final Member member;

    public CustomMemberDetails(Member member){
        this.member = member;
    }

    //유저 권한 가져오기
    @Override
    public List<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        log.debug("login user role = {}", member.getMemberRole());
        authorityList.add((GrantedAuthority) member::getMemberRole);
        return authorityList;
    }


    @Override
    public String getPassword() {
        return member.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberLoginId();
    }

    public long getMemberId(){ return  member.getId(); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
