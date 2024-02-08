package team.luckyturkey.communityservice.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String memberName;
    private String memberEmail;
    private String memberProfile;
    private String memberDate;
}
