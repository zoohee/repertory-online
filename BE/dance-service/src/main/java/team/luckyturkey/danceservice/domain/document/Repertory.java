package team.luckyturkey.danceservice.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@Getter
@Builder
@Document(collection = "repertory")
public class Repertory {
    @Transient
    public static final String SEQUENCE_NAME = "repertory_sequence";

    @Id
    @Setter
    private Long id;

    private String repertoryName;
    private Long memberId;
    private List<Long> sourceList;
    private boolean isRepertoryOpen;

    @Setter
    private String repertoryUrl;
    @Setter
    private String repertoryThumbnailUrl;
}
