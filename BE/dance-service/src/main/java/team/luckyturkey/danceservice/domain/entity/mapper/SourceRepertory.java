package team.luckyturkey.danceservice.domain.entity.mapper;

import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.domain.entity.Source;
import team.luckyturkey.danceservice.domain.entity.id.SourceRepertoryPK;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SourceRepertory {

    @Setter
    @EmbeddedId
    private SourceRepertoryPK id;

    @MapsId("sourceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

}
