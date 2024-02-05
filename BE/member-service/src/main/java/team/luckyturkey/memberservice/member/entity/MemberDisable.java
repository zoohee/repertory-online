package team.luckyturkey.memberservice.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MemberDisable {
    @Id
    private int id;

    private String memberDisableDate;
}
