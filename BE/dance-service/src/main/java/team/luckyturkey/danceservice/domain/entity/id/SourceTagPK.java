package team.luckyturkey.danceservice.domain.entity.id;

import jakarta.persistence.Column;
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
public class SourceTagPK implements Serializable {

    @Column (name = "source_id")
    private Long sourceId;

    @Column (name = "tag_id")
    private Long tagId;
}
