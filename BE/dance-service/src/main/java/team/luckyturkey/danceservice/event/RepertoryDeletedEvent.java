package team.luckyturkey.danceservice.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import team.luckyturkey.danceservice.domain.document.Repertory;

@Getter
@AllArgsConstructor
public class RepertoryDeletedEvent {
    private Repertory repertory;
}
