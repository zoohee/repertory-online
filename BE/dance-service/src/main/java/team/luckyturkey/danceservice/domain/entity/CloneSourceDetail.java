package team.luckyturkey.danceservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.domain.entity.id.CloneSourceDetailPK;

@ToString(exclude = {"cloneSource"})
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloneSourceDetail {

    @Setter @EmbeddedId
    private CloneSourceDetailPK cloneSourceDetailPK;

    @Setter
    @MapsId("cloneSourceId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clone_source_id")
    @JsonBackReference
    private CloneSource cloneSource;

    private String sourceName;
    private int sourceLength;
    private int sourceCount;
    private String sourceStart;
    private String sourceEnd;

}
