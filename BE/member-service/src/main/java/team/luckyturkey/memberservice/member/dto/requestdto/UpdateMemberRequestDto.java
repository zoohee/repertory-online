package team.luckyturkey.memberservice.member.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UpdateMemberRequestDto {

    private String memberLoginId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberProfile;

}


