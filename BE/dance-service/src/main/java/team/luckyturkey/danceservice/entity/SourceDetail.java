package team.luckyturkey.danceservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.entity.id.SourceDetailPK;

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

    private String sourceName;
    private int sourceLength;
    private boolean sourceIsOpen;
    private int sourceCount;
    private String sourceStart;
    private String sourceEnd;

}
