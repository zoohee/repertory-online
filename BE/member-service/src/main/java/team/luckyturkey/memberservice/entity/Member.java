package team.luckyturkey.memberservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity //어노테이션으로 엔티티 설정
@Getter
@Setter //롬복으로 게터세터 자동생성
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //아이덴티티값으로 설정해줌
    private int id;

    private String memberLoginId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberJoinDate;
    private int memberIsAvailable; //0 : active, 1 : quit
    private String memberProfile;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_role")
    private MemberRole memberRole;
//    private int memberRole;
//    private String role;

    public String getMemberRole(){
        return this.memberRole.getRole();
    }

    public void setMemberRole(String role){
        if(memberRole == null) memberRole = new MemberRole();
        memberRole.setRole(role);
    }
}
