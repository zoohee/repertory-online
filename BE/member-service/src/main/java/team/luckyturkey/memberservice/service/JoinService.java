package team.luckyturkey.memberservice.service;


import java.util.Date;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.Status.JoinRequestStatus;
import team.luckyturkey.memberservice.Status.LoginValidationStatus;
import team.luckyturkey.memberservice.Status.MemberAuthorityStatus;
import team.luckyturkey.memberservice.member.dto.requestdto.JoinRequestDto;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class JoinService {
    //주입
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입 진행 메서드 작성
    public JoinRequestStatus joinProcess(JoinRequestDto joinDTO){
        //앞단에서 날라오는 DTO를 받아야함

        String memberLoginId = joinDTO.getMemberLoginId();
        String memberPassword = joinDTO.getMemberPassword();
        String memberName = joinDTO.getMemberName();
        String memberEmail = joinDTO.getMemberEmail();

        //비어있는 항목이 있다면
        if(memberLoginId == null || memberPassword == null || memberName == null || memberEmail == null){
            return JoinRequestStatus.NULL_EXIST;
        }



        //유저 아이디로 중복체크
        Boolean isExist = memberRepository.existsByMemberLoginId(memberLoginId);


        if(isExist){
            return JoinRequestStatus.ID_DUPLICATED; //false ㄴㄴ
        }

        Member data = new Member();

        data.setMemberLoginId(memberLoginId);
        data.setMemberPassword(bCryptPasswordEncoder.encode(memberPassword));//패스워드는 인코딩 해야함
        data.setMemberName(memberName);
        data.setMemberEmail(memberEmail);
        //유저 롤 일단 나중에 고치기
        data.setMemberRole(MemberAuthorityStatus.ROLE_REGISTERED_MEMBER.getAuthority());
        //유저 네임과 패스워드를 요청받아서 넣을거임
        data.setMemberJoinDate(new Date().toString());

        memberRepository.save(data);
        return JoinRequestStatus.JOIN_SUCCESS;

    }

    private static final String ID_REGEX = "^[a-zA-Z0-9_-]{5,20}$";

    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,16}$";

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final String NAME_REGEX = "^[a-zA-Z가-힣0-9]{1,20}$";

    // ID 유효성 검사를 위한 정규식 패턴
    private static final Pattern ID_PATTERN = Pattern.compile(ID_REGEX);

    // 비밀번호 유효성 검사를 위한 정규식 패턴
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    // 이름 유효성 검사를 위한 정규식 패턴
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    // 이메일 유효성 검사를 위한 정규식 패턴
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    public static LoginValidationStatus validateJoinRequest(String memberLoginId, String memberPassword, String memberName, String memberEmail){
        if (!ID_PATTERN.matcher(memberLoginId).matches()) {
            return LoginValidationStatus.WRONG_FORM;
        }

        if (!PASSWORD_PATTERN.matcher(memberPassword).matches()) {
            return LoginValidationStatus.WRONG_FORM;
        }

        if (!NAME_PATTERN.matcher(memberName).matches()) {
            return LoginValidationStatus.WRONG_FORM;
        }

        if (!EMAIL_PATTERN.matcher(memberEmail).matches()) {
            return LoginValidationStatus.WRONG_FORM;
        }

        // 모든 조건에 부합하면 유효한 회원가입 요청임을 알림
        return LoginValidationStatus.VALID;
    }

}
