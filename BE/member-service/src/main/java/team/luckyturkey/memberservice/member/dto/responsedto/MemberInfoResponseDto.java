package team.luckyturkey.memberservice.member.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MemberInfoResponseDto {

    private String memberName;
    private String memberEmail;
    private String memberJoinDate;
    private String memberProfile;
    private String memberRole;


}
