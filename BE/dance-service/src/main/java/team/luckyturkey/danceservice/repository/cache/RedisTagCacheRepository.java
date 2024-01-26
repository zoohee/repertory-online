package team.luckyturkey.danceservice.repository.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import team.luckyturkey.danceservice.entity.Source;
import team.luckyturkey.danceservice.entity.Tag;
import team.luckyturkey.danceservice.repository.jpa.SourceTagRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.util.*;

@Slf4j
@Repository
//@DependsOn("dbSeeder")
public class RedisTagCacheRepository implements CacheTagRepository {


    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, Long, List<Long>> opsHash;

    @Autowired
    public RedisTagCacheRepository(
            RedisTemplate<String, String> redisTemplate,
            TagRepository tagRepository,
            SourceTagRepository tagSourceRepository
//            DBSeeder dbSeeder
    ) {
        this.redisTemplate = redisTemplate;
        this.opsHash = redisTemplate.opsForHash();
        cacheTagData(redisTemplate, tagRepository, tagSourceRepository);
    }


    //시작시 데이터들을 불러와 캐싱하는 과정
    private void cacheTagData(
            RedisTemplate<String, String> redisTemplate,
            TagRepository tagRepository,
            SourceTagRepository sourceTagRepository
    ) {
        List<Tag> tagList = tagRepository.findAll();

        //파이프라이닝을 통한 비동기 처리
        redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            //태그 별로 쿼리가 진행 되어야 한다. (hput 연산)
            for(Tag tag: tagList){
                //특정 tag가 붙은 source들을 불러온다.
                List<Source> sourceList = sourceTagRepository.findSourceListByTagId(tag.getId());


                //tag 안에 userid를 필드로 삼아, source 들을 분류해야 한다.
                Map<Long, List<Long>>  userSourceMap = new HashMap<>();
                for(Source source: sourceList){
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

    @Override
    public List<Long> findByTag(String tag) {
        List<List<Long>> values = opsHash.values(tag);
        List<Long> result = new ArrayList<>();
        for(List<Long> value: values){
            result.addAll(value);
        }
        return result;
    }

    @Override
    public void addSourceId(String tag, Long userId, Long sourceId) {
        List<Long> sourceIdList = opsHash.get(tag, userId);
        if(sourceIdList == null) sourceIdList = new ArrayList<>();

        sourceIdList.add(sourceId);
        opsHash.put(tag, userId, sourceIdList);
    }

    @Override
    public void deleteTag(String tag, Long userId) {
        opsHash.delete(tag, userId);
    }

    @Override
    public void deleteTagFromSource(String tag, Long userId, Long sourceId) {
        List<Long> longList = opsHash.get(tag, userId);
        Objects.requireNonNull(longList).remove(sourceId);
        opsHash.put(tag, userId, longList);
    }

    @Override
    public void deleteSourceId(List<String> tagList, Long userId, Long sourceId) {

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for(String tag: tagList){
                List<Long> sourceIdList = opsHash.get(tag, userId);
                boolean removed = Objects.requireNonNull(sourceIdList).remove(sourceId);

                if (removed) opsHash.put(tag, userId, sourceIdList);

            }
            return null;
        });
    }

}
