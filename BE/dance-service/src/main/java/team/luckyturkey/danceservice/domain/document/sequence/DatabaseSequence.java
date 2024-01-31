package team.luckyturkey.danceservice.domain.document.sequence;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Setter
@Document(collection = "database_sequence")
public class DatabaseSequence {

    @Id
    private String id;
    private Long sequence;

}


