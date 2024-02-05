package team.luckyturkey.memberservice.member.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAtuh2LoginRequestDto {
    private String name;
    private String loginId;
    private String email;
    private String role;
    private String profile;
    private String joinDate;
}
