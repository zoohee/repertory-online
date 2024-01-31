package team.luckyturkey.projectservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Project {

    @Id
    private Long _id;
    private String projectName;
    private Date projectDate;
    private Long userId;
    private List<Long> sourceList;
    private String projectThumbnailUrl;

}
