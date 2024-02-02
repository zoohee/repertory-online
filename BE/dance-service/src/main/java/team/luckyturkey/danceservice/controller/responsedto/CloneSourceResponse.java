package team.luckyturkey.danceservice.controller.responsedto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@Getter
public class CloneSourceResponse {

    private Long sourceId;
    private Long memberId;
    private String sourceName;
    private String sourceStart;
    private String sourceEnd;
    private int sourceLength;
    private int sourceCount;
    private String sourceUrl;
    private List<String> tagNameList;
}
