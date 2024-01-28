package team.luckyturkey.danceservice.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@ToString
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class    SourceDetailPK implements Serializable {

    @Column(name = "source_id")
    private Long sourceId;
}
