package team.luckyturkey.danceservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SourceDisabledEvent {
    private List<String> tagNameList;
    private Long memberId;
    private Long sourceId;
}
