package team.luckyturkey.memberservice.member.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MemberLoginIdIsExistDto {
    private String memberLoginId;
}
