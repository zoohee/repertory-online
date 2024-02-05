package team.luckyturkey.memberservice.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.memberservice.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> { // JPA 레포지토리 상속받고, 엔티티와 id의 레퍼런스 타입 입력받기
    
    //JPA 구문 중 existBy가 있음
    Boolean existsByMemberLoginId(String memberLoginId);

    //username을 받아 DB테이블에서 회원을 조회하는 메서드 작성
//    Member findByMemberLoginId(String memberLoginId);
    @Query("select m from Member m join fetch m.memberRole where m.memberLoginId = :memberLoginId")
    Member findByMemberLoginId(String memberLoginId);

    //이런 쿼리가 피료한건 조인이 필요한 연산이거나
    //너무 복잡한거
}
