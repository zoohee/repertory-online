package team.luckyturkey.danceservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Source{
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(mappedBy = "source", cascade = CascadeType.ALL)
    @JsonManagedReference
    private SourceDetail sourceDetail;

// todo: 이거 해결 해!!!!
//    @OneToMany(mappedBy = "source", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<SourceTag> tagList = new ArrayList<>();

    private String sourceUrl;
    private Long memberId;
    private LocalDateTime sourceDate;
}
