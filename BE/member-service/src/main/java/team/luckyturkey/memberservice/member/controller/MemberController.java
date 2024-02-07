package team.luckyturkey.memberservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.memberservice.JoinRequestStatus;
import team.luckyturkey.memberservice.dto.JoinRequestDto;
import team.luckyturkey.memberservice.dto.response.MemberInfoResponseDto;
import team.luckyturkey.memberservice.entity.Member;
import team.luckyturkey.memberservice.service.JoinService;
import team.luckyturkey.memberservice.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
//    @GetMapping("/")
//    public String mainP(){
//
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        //세션에서 이름 가져오기
//        SecurityContextHolder.getContext().getAuthentication().getName();
//
//        //세션에서 유저 롤 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        return "Main Controller : "+ name +" " + role;
//    }


    private final JoinService joinService;
    private final MemberService memberService;


    @GetMapping("/") //멤버 전체 불러오기
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> memberList = memberService.getAllMembers();

        // 사용자 목록을 반환하거나, 비어있는 경우 NOT_FOUND(404)를 반환할 수도 있습니다.
        return memberList.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(memberList);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(JoinRequestDto joinRequestDto){

        JoinRequestStatus status = joinService.joinProcess(joinRequestDto);

        return switch (status) {
            case NULL_EXIST -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            case ID_DUPLICATED -> new ResponseEntity<>(HttpStatus.CONFLICT);
            case JOIN_SUCCESS -> new ResponseEntity<>(HttpStatus.OK);
        };

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        // Spring Security에서 현재 인증 정보를 가져와 로그아웃 처리
        SecurityContextHolder.clearContext();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //todo : 회원 탈퇴
    //현재 로그인 사용자 == 탈퇴할 사용자
    //토큰이 만료되지 않았어야함

    //todo : 회원 정보 수정
    //현재 로그인 사용자 == 수정할 사용자
    //토큰이 만료되지 않았어야함

    @GetMapping("/following")
    public List<MemberInfoResponseDto> getFollowingMemberInfo(@RequestParam List<Long> followingList) {
        System.out.println(followingList);
        return memberService.getFollowingMemberInfo(followingList);
    }

    @GetMapping("/memberinfo/{memberId}")
    public MemberInfoResponseDto getMemberInfo(@PathVariable Long memberId) {
        return memberService.getMemberInfo(memberId);
    }




}
