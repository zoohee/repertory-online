package team.luckyturkey.danceservice.entity;

import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.entity.id.SourceTagPK;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SourceTag {

    @Setter
    @EmbeddedId
    private SourceTagPK id;

    @MapsId("sourceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
