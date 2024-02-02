package team.luckyturkey.danceservice.repository.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import team.luckyturkey.danceservice.domain.entity.Source;
import team.luckyturkey.danceservice.domain.entity.Tag;
import team.luckyturkey.danceservice.repository.jpa.SourceTagRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.util.*;

@Slf4j
@Repository
public class RedisTagCacheRepository implements CacheTagRepository, InitializingBean {


    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, Long, List<Long>> opsHash;
    private final TagRepository tagRepository;
    private final SourceTagRepository sourceTagRepository;
    public RedisTagCacheRepository(RedisTemplate<String, String> redisTemplate,
                                   TagRepository tagRepository,
                                   SourceTagRepository sourceTagRepository
    ) {
        this.redisTemplate = redisTemplate;
        this.opsHash = redisTemplate.opsForHash();
        this.sourceTagRepository = sourceTagRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void afterPropertiesSet() {
        cacheTagData(redisTemplate, tagRepository, sourceTagRepository);
    }

    //시작시 데이터들을 불러와 캐싱하는 과정
    private void cacheTagData(
            RedisTemplate<String, String> redisTemplate,
            TagRepository tagRepository,
            SourceTagRepository sourceTagRepository
    ) {
        List<Tag> tagList = tagRepository.findAll();

        Map<Tag, List<Source>> tagSourceMap = new HashMap<>();

        //특정 tag가 붙은 source들을 불러온다.
        for(Tag tag: tagList){
            tagSourceMap.put(tag, sourceTagRepository.findSourceListByTagId(tag.getId()));
        }

        //파이프라이닝을 통한 비동기 처리
        redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            //태그 별로 쿼리가 진행 되어야 한다. (hput 연산)
            for(Tag tag: tagList){

                //tag 안에 userid를 필드로 삼아, source 들을 분류해야 한다.
                Map<Long, List<Long>>  userSourceMap = new HashMap<>();
                for(Source source: tagSourceMap.get(tag)){
                    if(!userSourceMap.containsKey(source.getMemberId())) userSourceMap.put(source.getMemberId(), new ArrayList<>());
                    userSourceMap.get(source.getMemberId()).add(source.getId());
                }

                //key = user id, value = 해당 user의 source id list
                //tag : userId : sourceList 형식으로 redis에 데이터 삽입
                for(Long userId: userSourceMap.keySet()){
                    opsHash.put(tag.getTagName(), userId, userSourceMap.get(userId));
                }

            }
            return null;
        });
    }

    // 키 값이 다른 스키마와 겹칠 수 있음을 인지
    @Override
    public List<Long> findByTag(String tagName) {
        Set<String> keys = redisTemplate.keys("*" + tagName + "*");
        if(keys == null || keys.isEmpty()) return new ArrayList<>();

        List<Long> result = new ArrayList<>();

        for(String key: keys) {
            List<List<Long>> values = opsHash.values(key);
            for (List<Long> value : values) {
                result.addAll(value);
            }
        }
        return result;
    }

    /***
     * senario: user updates tags to sourceId (including saving new source)
     * 아 시발 진짜 못해먹겠네...
     * @param tagNameList
     * @param memberId
     * @param sourceId
     */
    @Override
    public void insertTagToSource(List<String> tagNameList, Long memberId, Long sourceId) {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for(String tagName : tagNameList) {
                List<Long> sourceIdList = opsHash.get(tagName, memberId);
                if (sourceIdList == null) sourceIdList = new ArrayList<>();

                sourceIdList.add(sourceId);
                opsHash.put(tagName, memberId, sourceIdList);
            }
            return null;
        });
    }

    @Override
    public void deleteTag(String tag, Long memberId) {
        opsHash.delete(tag, memberId);
    }

    @Override
    public void deleteTagFromSource(String tag, Long memberId, Long sourceId) {
        List<Long> longList = opsHash.get(tag, memberId);
        Objects.requireNonNull(longList).remove(sourceId);
        opsHash.put(tag, memberId, longList);
    }

    @Override
    public void deleteSourceId(List<String> tagList, Long memberId, Long sourceId) {

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for(String tag: tagList){
                List<Long> sourceIdList = opsHash.get(tag, memberId);
                boolean removed = Objects.requireNonNull(sourceIdList).remove(sourceId);

                if (removed) opsHash.put(tag, memberId, sourceIdList);

            }
            return null;
        });
    }

}
