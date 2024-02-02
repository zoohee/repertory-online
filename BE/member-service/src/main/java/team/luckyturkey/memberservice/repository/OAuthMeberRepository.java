package team.luckyturkey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.memberservice.entity.Member;

public interface OAuthMeberRepository extends JpaRepository<Member, Long> {

    //JPA 구문 중 existBy가 있음
    Boolean existsByMemberLoginId(String memberLoginId);

    //username을 받아 DB테이블에서 회원을 조회하는 메서드 작성
//    Member findByMemberLoginId(String memberLoginId);
    @Query("select m from Member m join fetch m.memberRole where m.memberLoginId = :memberLoginId")
    Member findByMemberLoginId(String memberLoginId);

}
