package team.luckyturkey.memberservice.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String memberLoginId;
    private String memberPassword;
}
