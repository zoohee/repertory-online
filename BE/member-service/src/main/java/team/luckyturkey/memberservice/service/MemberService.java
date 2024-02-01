package team.luckyturkey.memberservice.service;


import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.entity.Member;
import team.luckyturkey.memberservice.repository.MemberRepository;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }


}
