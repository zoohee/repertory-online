//package team.luckyturkey.memberservice.member.repository;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.SetOperations;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import java.util.Optional;
//import java.util.Set;
//
//@Slf4j
//@RequiredArgsConstructor
//public class RefreshTokenCustomRepositoryImpl implements RefreshTokenCustomRepository{
//
////    private final RedisTemplate<byte[], byte[]> redisTemplate;
//    private final StringRedisTemplate redisTemplate;
//    private final String KEY_PREFIX = "jwtToken:accessToken:";
//
//    @Override
//    public Optional<String> findIdByAccessToken(String accessToken) {
//        SetOperations<String, String> opsSet = redisTemplate.opsForSet();
//
//        String key = KEY_PREFIX + accessToken;
//        Set<String> members = opsSet.members(key);
//
//        if(members == null || members.isEmpty()) throw new RuntimeException("조회 결과가 없습니다.");
//        if(members.size() > 1) throw new RuntimeException("조회 결과가 다수 입니다");
//
//        return members.stream().findFirst();
//    }
//}
