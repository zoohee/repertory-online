package team.luckyturkey.danceservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDeletedEvent {
    private String tagName;
    private Long memberId;
}
