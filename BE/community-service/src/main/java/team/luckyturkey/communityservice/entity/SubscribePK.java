package team.luckyturkey.communityservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribePK implements Serializable {
    @Column(name = "member_id")
    private Long id;
    private Long followingMemberId;
}
