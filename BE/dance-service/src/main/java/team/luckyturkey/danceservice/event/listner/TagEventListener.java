package team.luckyturkey.danceservice.event.listner;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import team.luckyturkey.danceservice.event.TagDeletedEvent;
import team.luckyturkey.danceservice.repository.cache.CacheTagRepository;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Component
@RequiredArgsConstructor
public class TagEventListener {

    private final CacheTagRepository cacheTagRepository;

    @Transactional(REQUIRES_NEW)
    @EventListener
    public void tagDeletedEvent(TagDeletedEvent event){
        cacheTagRepository.deleteTag(event.getTagName(), event.getMemberId());
    }
}
