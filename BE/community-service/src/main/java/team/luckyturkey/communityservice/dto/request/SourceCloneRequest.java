package team.luckyturkey.communityservice.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SourceCloneRequest {
    private Long originId;
    private Long memberId;
}
