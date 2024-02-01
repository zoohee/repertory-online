package team.luckyturkey.memberservice.Status;


import jakarta.persistence.Enumerated;

public enum MemberAuthorityStatus {
    ROLE_ADMIN, // 관리자 권한
    ROLE_UNREGISTERED_USER, // 회원가입하지 않은 유저 권한
    ROLE_SOCIAL_LOGIN_USER, // 소셜 로그인은 했지만 회원가입은 하지 않은 유저 권한
    ROLE_REGISTERED_USER // 회원가입을 완료한 유저 권한

}
