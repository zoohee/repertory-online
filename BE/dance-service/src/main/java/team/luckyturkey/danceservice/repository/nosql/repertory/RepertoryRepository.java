package team.luckyturkey.danceservice.repository.nosql.repertory;

import org.springframework.data.mongodb.repository.MongoRepository;
import team.luckyturkey.danceservice.domain.document.Repertory;

import java.util.List;

public interface RepertoryRepository extends MongoRepository<Repertory, Long>, CustomRepertoryRepository {
    List<Repertory> findByMemberId(Long memberId);
    List<Repertory> findByRepertoryNameContaining(String keyword);
}
