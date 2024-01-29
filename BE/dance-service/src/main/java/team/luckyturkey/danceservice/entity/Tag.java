package team.luckyturkey.danceservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString(exclude = {"sourceTagList"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<SourceTag> sourceTagList = new ArrayList<>();

    private String tagName;
    private Long memberId;

    public List<SourceTag> getSourceTagList() {
        if(this.sourceTagList == null) this.sourceTagList = new ArrayList<>();
        return sourceTagList;
    }
}
