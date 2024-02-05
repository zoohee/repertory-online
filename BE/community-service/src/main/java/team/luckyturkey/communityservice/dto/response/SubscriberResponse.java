package team.luckyturkey.communityservice.dto.response;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberResponse {
    private Long memberId;
    private String memberName;
    private String memberProfile;
}
