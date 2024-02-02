package team.luckyturkey.danceservice.repository.cache;

import java.util.List;

public interface CacheTagRepository {

    List<Long> findByTag(String tagName);

    // source가 변경되었을때....
    void insertTagToSource(List<String> tagNameList, Long memberId, Long sourceId);


    //특정 사용자가 통으로 태그를 삭제 했을때
    void deleteTag(String tag, Long memberId);

    //특정 사용자가 소스에서 일부 태그만 삭제한 경우.... -> 현재 시나리오 없음 (update에 병합)
    void deleteTagFromSource(String tag, Long memberId, Long sourceId);

    //특정 사용자가 소스를 삭제 했을때
    void deleteSourceId(List<String> tagList, Long memberId, Long sourceId);

}
