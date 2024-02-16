package team.luckyturkey.memberservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MemberInfoResponseDto {
    private Long memberId;
    private String memberName;
    private String memberEmail;
    private String memberJoinDate;
    private String memberProfile;
    private String memberRole;
}

