package team.luckyturkey.danceservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class SourceTagModifiedEvent {

    private List<String> addedTagList;
    private List<String> deletedTagList;
    private Long memberId;
    private Long sourceId;
}
