package team.luckyturkey.memberservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.member.dto.requestdto.LoginDto;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.member.repository.MemberRepository;

@Service
public class LoginService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    public void loginProcess(LoginDto loginDTO){

        String memberLoginId = loginDTO.getMemberLoginId();
        String memberPassword = loginDTO.getMemberPassword();



        Member data = memberRepository.findByMemberLoginId(memberLoginId);

        //비어있는 항목이 있을 때

    }


}
