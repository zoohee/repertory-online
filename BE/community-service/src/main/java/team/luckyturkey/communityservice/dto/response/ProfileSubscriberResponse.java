package team.luckyturkey.communityservice.dto.response;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSubscriberResponse {
    private Long memberId;
    private String memberName;
    private int followerCount;
    private boolean isFollowed;
}
