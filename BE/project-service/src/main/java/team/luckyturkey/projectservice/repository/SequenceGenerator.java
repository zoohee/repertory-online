package team.luckyturkey.projectservice.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import team.luckyturkey.projectservice.document.sequence.DatabaseSequence;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class SequenceGenerator {
    private final MongoOperations mongoOps;

    public SequenceGenerator(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

    public Long generateSequence(String collectionName){
        DatabaseSequence nextSequence = mongoOps.findAndModify(query(where("_id").is(collectionName)),
                new Update().inc("sequence", 1),
                options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(nextSequence) ? nextSequence.getSequence() : 1;
    }
}
