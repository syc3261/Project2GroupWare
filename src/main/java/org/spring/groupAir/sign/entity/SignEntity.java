package org.spring.groupAir.sign.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.spring.groupAir.contraint.BaseTimeEntity;
import org.spring.groupAir.member.entity.MemberEntity;

import org.spring.groupAir.sign.dto.SignDto;


import javax.persistence.*;
import java.util.List;

@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "sign")
public class SignEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sign_id")
    private Long id;

    @Column(nullable = false)
    private String approve;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int signAttachFile;

    @Column(nullable = false)
    private String rejectReason;

    @Column(nullable = false)
    private String deptName;//부서명

    @Column(nullable = false)
    private String lastApprover; //최종승인자

    @Column(nullable = false)
    private String subContent;

    @Column(nullable = false)
    private String level; //직급

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signStatus_id")
    private SignStatusEntity signStatusEntity;

    @OneToMany(mappedBy = "signEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<SignFileEntity> signFileEntityList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private MemberEntity memberEntity;

    public static SignEntity towriteSignEntity(SignDto signDto) {
        SignEntity signEntity = new SignEntity();
        signEntity.setId(signDto.getId());
        signEntity.setApprove(signDto.getApprove());
        signEntity.setTitle(signDto.getTitle());
        signEntity.setContent(signDto.getContent());
        signEntity.setRejectReason(signDto.getRejectReason());
        signEntity.setDeptName(signDto.getDeptName());
        signEntity.setLastApprover(signDto.getLastApprover());
        signEntity.setSubContent(signDto.getSubContent());
        signEntity.setLevel(signDto.getLevel());

        return signEntity;
    }


    public static SignEntity toUpdateEntity(SignDto signDto) {
        SignEntity signEntity=new SignEntity();
        signEntity.setId(signDto.getId());
        signEntity.setApprove(signDto.getApprove());
        signEntity.setTitle(signDto.getTitle());
        signEntity.setContent(signDto.getContent());
        signEntity.setRejectReason(signDto.getRejectReason());
        signEntity.setSignAttachFile(signDto.getSignAttachFile());
        signEntity.setDeptName(signDto.getDeptName());
        signEntity.setLastApprover(signDto.getLastApprover());
        signEntity.setLevel(signDto.getLevel());
        signEntity.setMemberEntity(signDto.getMemberEntity());
        signEntity.setSubContent(signDto.getSubContent());

        return signEntity;





    }

    public static SignEntity toInsertSignEntity(SignDto signDto) {
        SignEntity signEntity=new SignEntity();
        signEntity.setId(signDto.getId());
        signEntity.setApprove(signDto.getApprove());
        signEntity.setTitle(signDto.getTitle());
        signEntity.setContent(signDto.getContent());
        signEntity.setSignAttachFile(0);
        signEntity.setMemberEntity(signDto.getMemberEntity());
        signEntity.setRejectReason(signDto.getRejectReason());
        signEntity.setDeptName(signDto.getDeptName());
        signEntity.setLastApprover(signDto.getLastApprover());
        signEntity.setSubContent(signDto.getSubContent());
        signEntity.setLevel(signDto.getLevel());

        return signEntity;
    }

    public static SignEntity toInsertFileSignEntity(SignDto signDto) {


        SignEntity signEntity=new SignEntity();
        signEntity.setId(signDto.getId());
        signEntity.setApprove(signDto.getApprove());
        signEntity.setTitle(signDto.getTitle());
        signEntity.setContent(signDto.getContent());
        signEntity.setSignAttachFile(1);
        signEntity.setMemberEntity(signDto.getMemberEntity());
        signEntity.setRejectReason(signDto.getRejectReason());
        signEntity.setDeptName(signDto.getDeptName());
        signEntity.setLastApprover(signDto.getLastApprover());
        signEntity.setSubContent(signDto.getSubContent());
        signEntity.setLevel(signDto.getLevel());
        return signEntity;






    }
}






