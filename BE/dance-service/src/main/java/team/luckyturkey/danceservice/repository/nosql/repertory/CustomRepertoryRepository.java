package team.luckyturkey.danceservice.repository.nosql.repertory;

import team.luckyturkey.danceservice.domain.document.Repertory;

public interface CustomRepertoryRepository {
    Repertory saveWithSequence(Repertory repertory);
    Repertory findAndUpdateVideoUrl(Long repertoryId, String repertoryUrl);

    Repertory findAndUpdateIsRepertoryOpen(Long repertoryId, boolean isRepertoryOpen);

    Repertory findAndDeleteById(Long repertoryId);
}
