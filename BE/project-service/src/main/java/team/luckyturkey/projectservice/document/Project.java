package team.luckyturkey.projectservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "project")
public class Project {

    @Transient
    public static final String SEQUENCE_NAME = "project_sequence";

    @Id
    @Setter
    private Long id;

    private String projectName;
    private Instant projectDate;
    private Long userId;
    private List<Long> sourceList;

    @Setter
    private String projectThumbnailUrl;

}
