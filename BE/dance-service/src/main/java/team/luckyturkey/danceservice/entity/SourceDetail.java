package team.luckyturkey.danceservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SourceDetail {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "source_id")
    private Source source;

    private String sourceName;
    private int sourceLength;
    private int sourceIsOpen;
    private int sourceCount;
    private String sourceStart;
    private String sourceEnd;
}
