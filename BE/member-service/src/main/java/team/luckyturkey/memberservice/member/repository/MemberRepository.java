package team.luckyturkey.memberservice.member.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.luckyturkey.memberservice.member.dto.requestdto.UpdateMemberRequestDto;
import team.luckyturkey.memberservice.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { // JPA 레포지토리 상속받고, 엔티티와 id의 레퍼런스 타입 입력받기

    //JPA 구문 중 existBy가 있음
    Boolean existsByMemberLoginId(String memberLoginId);

    Member findById(Long id);

    Member findByMemberLoginId(String memberLoginId);

    Member findByMemberEmail(String memberEmail);

    void deleteMemberByMemberLoginId(String memberEmail);

    String findMemberLoginIdByMemberEmail(String memberEmail);

}
