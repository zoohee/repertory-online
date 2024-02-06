package team.luckyturkey.memberservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.member.dto.requestdto.FindMemberLoginIdDto;
import team.luckyturkey.memberservice.member.dto.requestdto.MemberLoginIdIsExistDto;
import team.luckyturkey.memberservice.member.dto.requestdto.UpdateMemberRequestDto;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.member.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member getMemberByLoginId(String memberLoginId){

        return memberRepository.findByMemberLoginId(memberLoginId);
    }

    @Transactional
    public void quitMember(String memberLoginId){

        memberRepository.deleteMemberByMemberLoginId(memberLoginId);
    }

    @Transactional
    public  void updateMember(UpdateMemberRequestDto updateMemberRequestDto){
        String currentLoggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByMemberEamil(currentLoggedInUserEmail);

        member.setMemberName(updateMemberRequestDto.getMemberName());
        member.setMemberPassword(bCryptPasswordEncoder.encode(updateMemberRequestDto.getMemberPassword()));
        member.setMemberProfile(updateMemberRequestDto.getMemberProfile());

        memberRepository.save(member);
    }


    @Transactional
    public Boolean memberLoginIdIsExist(MemberLoginIdIsExistDto memberLoginIdIsExistDto){
        return memberRepository.existsByMemberLoginId(memberLoginIdIsExistDto.getMemberLoginId());
    }

    @Transactional
    public String findMemberLoginId(FindMemberLoginIdDto findMemberLoginIdDto){
        String loginId = memberRepository.findMemberLoginIdByMemberEmail(findMemberLoginIdDto.getMemberEmail());

        return loginId;
    }

}
