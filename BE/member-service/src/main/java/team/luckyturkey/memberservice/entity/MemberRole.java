package team.luckyturkey.memberservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MemberRole {

    @Id
    private int id;

    private String role;
}
