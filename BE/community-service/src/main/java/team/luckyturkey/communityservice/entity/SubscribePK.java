package team.luckyturkey.communityservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Getter
@Setter
public class SubscribePK implements Serializable {
    @Column(name = "member_id")
    private Long id;
    private Long followingMemberId;
}
