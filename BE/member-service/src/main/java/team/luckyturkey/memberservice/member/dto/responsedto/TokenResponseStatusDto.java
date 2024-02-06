package team.luckyturkey.memberservice.member.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponseStatusDto {
    private Integer status;
    private String newAccessToken;

    public static TokenResponseStatusDto addStatus(Integer status, String newAccessToken){
        return new TokenResponseStatusDto(status, newAccessToken);
    }


}
