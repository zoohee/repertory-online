package team.luckyturkey.memberservice.Status;


import lombok.Getter;

@Getter
public enum MemberAuthorityStatus {
    ROLE_ADMIN("ROLE_ADMIN"), // 관리자 권한
    ROLE_UNREGISTERED_MEMBER("ROLE_UNREGISTERED_MEMBER"), // 회원가입하지 않은 유저 권한
    ROLE_SOCIAL_LOGIN_MEMBER("ROLE_SOCIAL_LOGIN_MEMBER"), // 소셜 로그인은 했지만 회원가입은 하지 않은 유저 권한
    ROLE_REGISTERED_MEMBER("ROLE_REGISTERED_MEMBER"); // 회원가입을 완료한 유저 권한

    private final String authority;

    MemberAuthorityStatus(String authority) {
        this.authority = authority;
    }

}
