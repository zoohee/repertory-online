package team.luckyturkey.memberservice.member.dto.responsedto;

import lombok.*;

@ToString
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMemberInfoResponseDto {
    private Long memberId;
    private String memberName;
    private String memberProfile;
    
}
