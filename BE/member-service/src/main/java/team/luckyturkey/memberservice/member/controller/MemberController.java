package team.luckyturkey.memberservice.member.controller;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.memberservice.Status.JoinRequestStatus;
import team.luckyturkey.memberservice.auth.jwt.RefreshToken;
import team.luckyturkey.memberservice.member.dto.requestdto.*;
import team.luckyturkey.memberservice.member.dto.responsedto.CommunityMemberInfoResponseDto;
import team.luckyturkey.memberservice.member.dto.responsedto.MemberInfoResponseDto;
import team.luckyturkey.memberservice.member.entity.Member;
import team.luckyturkey.memberservice.service.JoinService;
import team.luckyturkey.memberservice.service.MemberService;
import team.luckyturkey.memberservice.service.RefreshTokenService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {


    private final JoinService joinService;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;


    @GetMapping("/") //멤버 전체 불러오기
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> memberList = memberService.getAllMembers();

        // 사용자 목록을 반환하거나, 비어있는 경우 NOT_FOUND(404)를 반환할 수도 있습니다.
        return memberList.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(memberList);
    }



    //회원 탈퇴
    @DeleteMapping("/")
    public ResponseEntity<?> quitMember(@RequestBody QuitMemberRequestDto quitMemberRequestDto){
        String currentLoggedInMemberId = SecurityContextHolder.getContext().getAuthentication().getName();
        //현재 로그인한 사용자 아이디 != 요청받은 아이디 라면 반환
        if(!currentLoggedInMemberId.equals(quitMemberRequestDto.getMemberLoginId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        memberService.quitMember(quitMemberRequestDto.getMemberLoginId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //회원 정보 수정
    @PatchMapping("/")
    public ResponseEntity<?> updateMemberInfo(@RequestBody UpdateMemberRequestDto updateMemberRequestDto){

        String currentLoggedInMemberId = SecurityContextHolder.getContext().getAuthentication().getName();
        //현재 로그인한 사용자 아이디 != 요청받은 아이디 라면 반환
        if(!currentLoggedInMemberId.equals(updateMemberRequestDto.getMemberLoginId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memberService.updateMember(updateMemberRequestDto);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(@RequestBody JoinRequestDto joinRequestDto){

        JoinRequestStatus status = joinService.joinProcess(joinRequestDto);

        return switch (status) {
            case NULL_EXIST -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            case ID_DUPLICATED -> new ResponseEntity<>(HttpStatus.CONFLICT);
            case JOIN_SUCCESS -> new ResponseEntity<>(HttpStatus.OK);
        };
    }


    @GetMapping("/id-validation")
    public ResponseEntity<?> validateMemberLoginId(@RequestParam String memberLoginId){
        if(memberService.memberLoginIdIsExist(memberLoginId)){
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find-id")
    public ResponseEntity<?> findMemberLoginId(@RequestParam String memberEmail){

        String id = memberService.findMemberLoginId(memberEmail);
        if(id == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    @GetMapping("/memberinfo/{memberId}")//주희꺼
    public MemberInfoResponseDto getMemberInfoById(@PathVariable Long memberId) {
        return memberService.getMemberInfo(memberId);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getMemberInfo(@RequestParam String memberLoginId){
        //유저 아이디로 검색해서 있는지부터 확인
        Member Exist = memberService.getMemberByLoginId(memberLoginId);

        if(Exist == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //있으면 인포 가져오기
        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();

        memberInfoResponseDto.setMemberName(Exist.getMemberName());
        memberInfoResponseDto.setMemberEmail(Exist.getMemberEmail());
        memberInfoResponseDto.setMemberJoinDate(Exist.getMemberJoinDate());
        memberInfoResponseDto.setMemberProfile(Exist.getMemberProfile());
        memberInfoResponseDto.setMemberRole(Exist.getMemberRole());

        return new ResponseEntity<>(memberInfoResponseDto, HttpStatus.OK);
    }

    @GetMapping("/following")
    public List<CommunityMemberInfoResponseDto> getFollowingMemberInfo(@RequestParam List<Long> followingList) {
        return memberService.getFollowingMemberInfo(followingList);
    }



    @GetMapping("/search/{keyword}")
    public List<CommunityMemberInfoResponseDto> searchByMemberName(@PathVariable String keyword) {
        return memberService.searchByMemberName(keyword);
    }


    @GetMapping("/loginsuccess")
    public ResponseEntity<String> handleLoginSuccess(@RequestParam("accessToken") String accessToken) {
        // accessToken을 파라미터에서 받아옵니다.

        // 작업 수행 후 응답을 헤더에 담아서 반환합니다.
        String responseBody = "형진아 힘내";
        //액세스 토큰으로 검색해서 리프레시토큰 가져오기
        RefreshToken token = refreshTokenService.findToken(accessToken);
        String refreshToken = token.getRefreshToken();

        return ResponseEntity.ok()
                .header("Authorization", accessToken)
                .header("Refresh", refreshToken)// 원하는 헤더에 정보를 담아줍니다.
                .body(responseBody);
    }

}
