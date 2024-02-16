package team.luckyturkey.memberservice.member.dto.responsedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CommunityMemberInfoResponseDto {
    private Long memberId;
    private String memberName;
    private String memberProfile;
    
}
