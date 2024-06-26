package team.luckyturkey.memberservice.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.memberservice.member.dto.responsedto.CommunityMemberInfoResponseDto;
import team.luckyturkey.memberservice.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { // JPA 레포지토리 상속받고, 엔티티와 id의 레퍼런스 타입 입력받기

    //JPA 구문 중 existBy가 있음
    Boolean existsByMemberLoginId(String memberLoginId);

    Optional<Member> findById(Long id);

    Member findByMemberLoginId(String memberLoginId);

    Member findByMemberEmail(String memberEmail);

    void deleteMemberByMemberLoginId(String memberEmail);

    String findMemberLoginIdByMemberEmail(String memberEmail);

    @Query("select m from Member m where m.memberName like %:keyword%")
    List<Member> findByMemberNameLike(String keyword);
}
