package team.luckyturkey.danceservice.entity;

import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.entity.id.SourceTagPK;

@Entity
@IdClass(SourceTagPK.class)
@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SourceTag {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
