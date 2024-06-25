package org.spring.groupAir.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.commute.entity.CommuteEntity;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.MemberFileEntity;
import org.spring.groupAir.member.entity.PositionEntity;
import org.spring.groupAir.role.Role;

import org.spring.groupAir.salary.entity.SalaryEntity;
import org.spring.groupAir.schedule.entity.ScheduleEntity;
import org.spring.groupAir.sign.entity.SignEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberDto {

    private Long id;

    private Role role;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Size(min = 3,max = 255)
    @NotBlank(message = "이메일을 입력해주세요")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String userPw;

    private String address;

    private String phone;

    private String employeeDate;

    private String resignationDate;

    private int memberAttachFile;

    private String memberNewFileName;

    private String memberOldFileName;

    private String memberFileName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<MemberFileEntity> memberFileEntityList;

    private MultipartFile memberFile;

    private List<BoardEntity> boardEntityList;

    private List<CommuteEntity> commuteEntityList;

    private List<SalaryEntity> salaryEntityList;

    private List<ScheduleEntity> scheduleEntityList;

    private DepartmentEntity departmentEntity;

    private PositionEntity positionEntity;

    private List<SignEntity> signEntityList;

    private Long positionId;
    private Long departmentId;


    public static MemberDto toMemberDto(MemberEntity memberEntity){

        MemberDto memberDto = new MemberDto();

        memberDto.setId(memberEntity.getId());
        memberDto.setUserEmail(memberEntity.getUserEmail());
        memberDto.setUserPw(memberEntity.getUserPw());
        memberDto.setAddress(memberEntity.getAddress());
        memberDto.setName(memberEntity.getName());
        memberDto.setPhone(memberEntity.getPhone());
        memberDto.setName(memberEntity.getName());
        memberDto.setRole(memberEntity.getRole());
        memberDto.setEmployeeDate(memberEntity.getEmployeeDate());
        memberDto.setResignationDate(memberEntity.getResignationDate());
        memberDto.setMemberFileEntityList(memberEntity.getMemberFileEntityList());
        memberDto.setBoardEntityList(memberEntity.getBoardEntityList());
        memberDto.setCommuteEntityList(memberEntity.getCommuteEntityList());
        memberDto.setSalaryEntityList(memberEntity.getSalaryEntityList());
        memberDto.setScheduleEntityList(memberEntity.getScheduleEntityList());
        memberDto.setDepartmentEntity(memberEntity.getDepartmentEntity());
        memberDto.setPositionEntity(memberEntity.getPositionEntity());
        memberDto.setSignEntityList(memberEntity.getSignEntityList());
        memberDto.setCreateTime(memberEntity.getCreateTime());
        memberDto.setUpdateTime(memberEntity.getUpdateTime());
        if(memberEntity.getMemberAttachFile()==0){
            //파일 x
            memberDto.setMemberAttachFile(memberDto.getMemberAttachFile());
        }else {
            memberDto.setMemberAttachFile(memberDto.getMemberAttachFile());
            memberDto.setMemberNewFileName(memberEntity.getMemberFileEntityList().get(0).getMemberNewFile());
            memberDto.setMemberOldFileName(memberEntity.getMemberFileEntityList().get(0).getMemberOldFile());
        }

        if (memberEntity.getMemberAttachFile() == 0) {
            memberDto.setMemberAttachFile(memberEntity.getMemberAttachFile());
        } else {
            memberDto.setMemberAttachFile(memberEntity.getMemberAttachFile());
            memberDto.setMemberFileName(memberEntity.getMemberFileEntityList().get(0).getMemberNewFile());
            memberDto.setMemberOldFileName(memberEntity.getMemberFileEntityList().get(0).getMemberOldFile());
            memberDto.setMemberNewFileName(memberEntity.getMemberFileEntityList().get(0).getMemberNewFile());
        }

        return memberDto;
    }
}
