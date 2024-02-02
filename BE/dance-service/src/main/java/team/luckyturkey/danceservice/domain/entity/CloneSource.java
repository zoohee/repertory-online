package team.luckyturkey.danceservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import team.luckyturkey.danceservice.util.StringListConverter;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloneSource {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @OneToOne(mappedBy = "cloneSource", cascade = CascadeType.ALL)
    @JsonManagedReference
    private CloneSourceDetail cloneSourceDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_id")
    private Source source;


    private Long memberId;
    private LocalDateTime cloneDate;
    @Convert(converter = StringListConverter.class)
    private List<String> tagName;

    public String getSourceName() {
        return this.cloneSourceDetail.getSourceName();
    }

    public int getSourceLength() {
        return this.cloneSourceDetail.getSourceLength();
    }

    public int getSourceCount() {
        return this.cloneSourceDetail.getSourceCount();
    }

    public String getSourceStart() {
        return this.cloneSourceDetail.getSourceStart();
    }

    public String getSourceEnd() {
        return this.cloneSourceDetail.getSourceEnd();
    }

    public String getSourceUrl(){
        return this.source.getSourceUrl();
    }
// todo
//    public List<Tag> getTagList(){
//        return this.cloneSourceDetail.
//    }
}
