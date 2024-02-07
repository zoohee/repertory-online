package team.luckyturkey.memberservice.service;


import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.dto.response.MemberInfoResponseDto;
import team.luckyturkey.memberservice.entity.Member;
import team.luckyturkey.memberservice.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public List<MemberInfoResponseDto> getFollowingMemberInfo(List<Long> followingList) {
        List<MemberInfoResponseDto> followingMemberInfoList = new ArrayList<>();
        for (Long id : followingList) {
            Optional<Member> optionalMember = memberRepository.findById(id);
//            System.out.println(optionalMember);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();
                memberInfoResponseDto.setMemberName(member.getMemberName());
                memberInfoResponseDto.setMemberProfile(member.getMemberProfile());
                followingMemberInfoList.add(memberInfoResponseDto);
            } else {
                // 해당 ID에 해당하는 회원이 없는 경우에 대한 처리
                // 예를 들어, 예외를 던지거나 다른 처리를 수행할 수 있습니다.
                // throw new RuntimeException("Member not found for ID: " + id);
            }
        }
        return followingMemberInfoList;
    }

    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            memberInfoResponseDto.setMemberName(member.getMemberName());
            memberInfoResponseDto.setMemberProfile(member.getMemberProfile());

        } else {
//            throw new RuntimeException();
        }
        return memberInfoResponseDto;
    }
}
