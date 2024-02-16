package team.luckyturkey.memberservice.service;


import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.Status.JoinRequestStatus;
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
}
