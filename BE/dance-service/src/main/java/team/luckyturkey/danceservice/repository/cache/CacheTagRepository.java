package team.luckyturkey.danceservice.repository.cache;

import java.util.List;

public interface CacheTagRepository {

    List<Long> findByTag(String tag);
    void updateSource(List<String> tagNameList, Long userId, Long sourceId);


    //특정 사용자가 통으로 태그를 삭제 했을때
    void deleteTag(String tag, Long userId);

    //특정 사용자가 소스에서 일부 태그만 삭제한 경우....
    void deleteTagFromSource(String tag, Long userId, Long sourceId);

    //특정 사용자가 소스를 삭제 했을때
    void deleteSourceId(List<String> tagList, Long userId, Long sourceId);

}
