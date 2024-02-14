package team.luckyturkey.memberservice.service;


import jakarta.transaction.Transactional;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team.luckyturkey.memberservice.member.dto.requestdto.FindMemberLoginIdDto;
import team.luckyturkey.memberservice.member.dto.requestdto.MemberLoginIdIsExistDto;
import team.luckyturkey.memberservice.member.dto.requestdto.UpdateMemberRequestDto;
import team.luckyturkey.memberservice.member.dto.responsedto.CommunityMemberInfoResponseDto;
import team.luckyturkey.memberservice.member.dto.responsedto.MemberInfoResponseDto;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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

        Member member = memberRepository.findByMemberEmail(currentLoggedInUserEmail);

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

    public List<CommunityMemberInfoResponseDto> getFollowingMemberInfo(List<Long> followingList) {
        List<CommunityMemberInfoResponseDto> followingMemberInfoList = new ArrayList<>();
        for (Long id : followingList) {
            Optional<Member> optionalMember = memberRepository.findById(id);

            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                CommunityMemberInfoResponseDto memberInfoResponseDto = CommunityMemberInfoResponseDto
                        .builder()
                        .memberId(id)
                        .memberProfile(member.getMemberProfile())
                        .memberName(member.getMemberName())
                        .build();

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
            memberInfoResponseDto.setMemberId(member.getId());
            memberInfoResponseDto.setMemberName(member.getMemberName());
            memberInfoResponseDto.setMemberProfile(member.getMemberProfile());

        } else {
//            throw new RuntimeException();
        }
        return memberInfoResponseDto;
    }

    public List<CommunityMemberInfoResponseDto> searchByMemberName(String keyword) {
        List<CommunityMemberInfoResponseDto> memberInfoList = new ArrayList<>();

        List<Member> members = memberRepository.findByMemberNameLike(keyword);
        for (Member m : members) {
            CommunityMemberInfoResponseDto c = CommunityMemberInfoResponseDto.builder()
                    .memberId(m.getId())
                    .memberName(m.getMemberName())
                    .memberProfile(m.getMemberProfile())
                    .build();
            memberInfoList.add(c);
        }

        return memberInfoList;
    }
}
