package team.luckyturkey.communityservice.dto.request;

import lombok.Builder;

@Builder
public class SourceCloneRequest {
    private Long originId;
    private Long memberId;
}
