package team.luckyturkey.danceservice.domain.entity.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CloneSourceDetailPK implements Serializable {
    private Long cloneSourceId;
}
