package team.luckyturkey.danceservice.event.listner;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import team.luckyturkey.danceservice.event.SourceDisabledEvent;
import team.luckyturkey.danceservice.event.SourceEnabledEvent;
import team.luckyturkey.danceservice.repository.cache.CacheTagRepository;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Component
@RequiredArgsConstructor
public class SourceEventListener {

    private final CacheTagRepository cacheTagRepository;
    @Transactional(REQUIRES_NEW)
    @EventListener
    public void sourceEnabledEvent(SourceEnabledEvent event){
        cacheTagRepository.updateSource(event.getTagNameList(), event.getUserId(), event.getSourceId());
    }

    @Transactional(REQUIRES_NEW)
    @EventListener
    public void sourceDisabledEvent(SourceDisabledEvent event){
        cacheTagRepository.deleteSourceId(event.getTagNameList(), event.getUserId(), event.getSourceId());
    }
}
