package team.luckyturkey.danceservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.domain.entity.id.SourceDetailPK;

@Entity
@Getter
@Builder
@ToString(exclude = "source")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SourceDetail {

    @Setter
    @EmbeddedId
    private SourceDetailPK id;

    @Setter
    @MapsId("sourceId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    @JsonBackReference
    private Source source;

    @Setter
    private boolean isSourceOpen;

    private String sourceName;
    private int sourceLength;
    private int sourceCount;
    private String sourceStart;
    private String sourceEnd;

}
