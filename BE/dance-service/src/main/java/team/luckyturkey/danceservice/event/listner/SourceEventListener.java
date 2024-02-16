package team.luckyturkey.danceservice.event.listner;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import team.luckyturkey.danceservice.event.SourceDisabledEvent;
import team.luckyturkey.danceservice.event.SourceEnabledEvent;
import team.luckyturkey.danceservice.event.SourceTagModifiedEvent;
import team.luckyturkey.danceservice.repository.cache.CacheTagRepository;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Component
@RequiredArgsConstructor
public class SourceEventListener {

    private final CacheTagRepository cacheTagRepository;
    @Transactional(REQUIRES_NEW)
    @EventListener
    public void sourceEnabledEvent(SourceEnabledEvent event){
        cacheTagRepository.insertTagToSource(event.getTagNameList(), event.getUserId(), event.getSourceId());
    }

    @Transactional(REQUIRES_NEW)
    @EventListener
    public void sourceDisabledEvent(SourceDisabledEvent event){
        cacheTagRepository.deleteSourceId(event.getTagNameList(), event.getMemberId(), event.getSourceId());
    }
    @Transactional(REQUIRES_NEW)
    @EventListener
    public void sourceTagModifiedEvent(SourceTagModifiedEvent event){
        List<String> addedTagList = event.getAddedTagList();
        List<String> deletedTagList = event.getDeletedTagList();
        Long sourceId = event.getSourceId();
        Long memberId = event.getMemberId();

        if(!addedTagList.isEmpty())
            cacheTagRepository.insertTagToSource(addedTagList, memberId, sourceId);

        if(!deletedTagList.isEmpty())
            cacheTagRepository.deleteSourceId(deletedTagList, memberId, sourceId);
    }


}
