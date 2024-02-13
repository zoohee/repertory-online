package team.luckyturkey.communityservice.dto.response;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSubscriberResponse {
    private Long memberId;
    private int followerCount;
    private boolean isFollowed;
}
