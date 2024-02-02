package team.luckyturkey.memberservice.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class JoinRequestDto {
    private String memberLoginId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;

}
