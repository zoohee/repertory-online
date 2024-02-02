package team.luckyturkey.danceservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.domain.entity.mapper.SourceTag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString(exclude = {"sourceTagList"})
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Source{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private Long id;

    @Setter
    @OneToOne(mappedBy = "source", cascade = CascadeType.ALL)
    @JsonManagedReference
    private SourceDetail sourceDetail;

    @OneToMany(mappedBy = "source", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SourceTag> sourceTagList = new ArrayList<>();

    private String sourceUrl;
    private Long memberId;
    private LocalDateTime sourceDate;

    public String getSourceName() {
        return sourceDetail.getSourceName();
    }

    public int getSourceLength() {
        return sourceDetail.getSourceLength();
    }

    public boolean isSourceIsOpen() {
        return sourceDetail.isSourceOpen();
    }

    public int getSourceCount() {
        return sourceDetail.getSourceCount();
    }

    public String getSourceStart() {
        return sourceDetail.getSourceStart();
    }

    public String getSourceEnd() {
        return sourceDetail.getSourceEnd();
    }

    public List<SourceTag> getSourceTagList() {
        if(this.sourceTagList == null) sourceTagList = new ArrayList<>();
        return sourceTagList;
    }

    public List<Tag> getTagList(){
        if(this.sourceTagList == null) {
            this.sourceTagList = new ArrayList<>();
        }

        List<Tag> result = new ArrayList<>();
        for(SourceTag sourceTag: sourceTagList){
            result.add(sourceTag.getTag());
        }
        return result;
    }
}
