package team.luckyturkey.communityservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String memberName;
    private String memberEmail;
    private String memberProfile;
    private String memberDate;
}
