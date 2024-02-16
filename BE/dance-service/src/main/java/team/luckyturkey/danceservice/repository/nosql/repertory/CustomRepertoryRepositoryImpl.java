package team.luckyturkey.danceservice.repository.nosql.repertory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import team.luckyturkey.danceservice.domain.document.Repertory;
import team.luckyturkey.danceservice.repository.nosql.SequenceGenerator;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@RequiredArgsConstructor
public class CustomRepertoryRepositoryImpl implements CustomRepertoryRepository{
    private final MongoTemplate mongoTemplate;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public Repertory saveWithSequence(Repertory repertory) {
        Long nextSequence = sequenceGenerator.generateSequence(Repertory.SEQUENCE_NAME);
        repertory.setId(nextSequence);

        return mongoTemplate.save(repertory);
    }

    @Override
    public Repertory findAndUpdateVideoUrl(Long repertoryId, String repertoryVideoUrl) {
        return mongoTemplate.findAndModify(
                query(where("_id").is(repertoryId)),
                update("repertoryUrl", repertoryVideoUrl),
                FindAndModifyOptions.options().returnNew(true),
                Repertory.class);

    }


    @Override
    public Repertory findAndUpdateIsRepertoryOpen(Long repertoryId, boolean isRepertoryOpen) {
        return mongoTemplate.findAndModify(
                query(where("_id").is(repertoryId)),
                update("isRepertoryOpen", isRepertoryOpen),
                FindAndModifyOptions.options().returnNew(true),
                Repertory.class);
    }

    @Override
    public Repertory findAndDeleteById(Long repertoryId) {
        return mongoTemplate.findAndRemove(query(where("_id").is(repertoryId)),
                Repertory.class);
    }

}
