package team.luckyturkey.danceservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SourceEnabledEvent {
    private List<String> tagNameList;
    private Long userId;
    private Long sourceId;
}
