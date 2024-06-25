package org.spring.groupAir.sign.dto;

import lombok.*;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.sign.entity.SignEntity;
import org.spring.groupAir.sign.entity.SignFileEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignDto {
    private Long id;


    private String approve;


    private String title;


    private String content;


    private int signAttachFile;


    private String rejectReason;// 비고

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String signNewFile;

    private String signOldFile;

    private MultipartFile signFile;

    private List<SignFileEntity> signFileEntityList;

    private MemberEntity memberEntity;

    private Long employee_id;


    private String deptName;//부서명

    private String lastApprover; //최종승인자


    private String subContent; //승인 , 반려


    private String level; //직급

    public SignDto(SignEntity signEntity) {

    }


    public static SignDto toSelectSignDto(SignEntity signEntity) {

        SignDto signDto = new SignDto();
        signDto.setId(signEntity.getId());
        signDto.setApprove(signEntity.getApprove());
        signDto.setTitle(signEntity.getTitle());
        signDto.setContent(signEntity.getContent());
        signDto.setSignAttachFile(signEntity.getSignAttachFile());
        signDto.setRejectReason(signEntity.getRejectReason());
        signDto.setCreateTime(signEntity.getCreateTime());
        signDto.setUpdateTime(signEntity.getUpdateTime());
        signDto.setSubContent(signEntity.getSubContent());//결재상태


        return signDto;

    }

    public static SignDto toSignDto(SignEntity signEntity) {

        SignDto signDto = new SignDto();
        signDto.setId(signEntity.getId());
        signDto.setApprove(signEntity.getApprove());
        signDto.setTitle(signEntity.getTitle());
        signDto.setContent(signEntity.getContent());
        signDto.setRejectReason(signEntity.getRejectReason());
        signDto.setCreateTime(signEntity.getCreateTime());
        signDto.setUpdateTime(signEntity.getUpdateTime());
        signDto.setEmployee_id(signEntity.getMemberEntity().getId());
        signDto.setDeptName(signEntity.getDeptName()); // 부서명 설정
        signDto.setLevel(signEntity.getLevel()); // 직급 설정
        signDto.setLastApprover(signEntity.getLastApprover()); // 최종승인자 설정
        signDto.setSubContent(signEntity.getSubContent());//결재상태

        System.out.println(signEntity.getSignAttachFile()+" <<<");
        if (signEntity.getSignAttachFile() == 1) {
            signDto.setSignAttachFile(signEntity.getSignAttachFile());
           signDto.setSignNewFile(signEntity.getSignFileEntityList().get(0).getSignNewFile());
           signDto.setSignOldFile(signEntity.getSignFileEntityList().get(0).getSignOldFile());
        } else {
            signDto.setSignAttachFile(signEntity.getSignAttachFile());
        }
        return signDto;


    }
}


