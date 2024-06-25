package org.spring.groupAir.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.commute.entity.CommuteEntity;
import org.spring.groupAir.commute.entity.VacationEntity;
import org.spring.groupAir.contraint.BaseTimeEntity;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.role.Role;
import org.spring.groupAir.salary.entity.SalaryEntity;
import org.spring.groupAir.schedule.entity.ScheduleEntity;
import org.spring.groupAir.sign.entity.SignEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "employee")
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    // id = employee_id

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String employeeDate;

    @Column(nullable = true)
    private String resignationDate;

    @Column(nullable = true)
    private int memberAttachFile;

    @Column(nullable = true)
    private String memberFileName;

    @OneToMany(mappedBy = "memberEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<MemberFileEntity> memberFileEntityList;

    @OneToMany(mappedBy = "memberEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<BoardEntity> boardEntityList;

    @OneToMany(mappedBy = "memberEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<CommuteEntity> commuteEntityList;

    @OneToMany(mappedBy = "memberEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<VacationEntity> vacationEntityList;

    @OneToMany(mappedBy = "memberEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<SalaryEntity> salaryEntityList;

    @OneToMany(mappedBy = "memberEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<ScheduleEntity> scheduleEntityList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private DepartmentEntity departmentEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private PositionEntity positionEntity;

    @OneToMany(mappedBy = "memberEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<SignEntity> signEntityList;


    public static MemberEntity toMemberJoinEntity0(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setName(memberDto.getName());
        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setEmployeeDate(memberDto.getEmployeeDate());
        memberEntity.setResignationDate(memberDto.getResignationDate());
        memberEntity.setDepartmentEntity(memberDto.getDepartmentEntity());
        memberEntity.setPositionEntity(memberDto.getPositionEntity());

        if(memberDto.getPositionEntity().getPositionName().equals("대표이사")){
            memberEntity.setRole(Role.ADMIN);
        }
        else if(memberDto.getPositionEntity().getPositionName().equals("부장")){
            memberEntity.setRole(Role.MANAGER);
        }
        else if(memberDto.getPositionEntity().getPositionName().equals("사원")){
            memberEntity.setRole(Role.MEMBER);
        }
        memberEntity.setPhone(memberDto.getPhone());

        memberEntity.setMemberAttachFile(0);
        memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());
        memberEntity.setMemberFileName(memberDto.getMemberFileName());
        return memberEntity;
    }
    public static MemberEntity toMemberJoinEntity1(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setName(memberDto.getName());
        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setEmployeeDate(memberDto.getEmployeeDate());
        memberEntity.setResignationDate(memberDto.getResignationDate());
        memberEntity.setDepartmentEntity(memberDto.getDepartmentEntity());
        memberEntity.setPositionEntity(memberDto.getPositionEntity());

        if(memberDto.getPositionEntity().getPositionName().equals("대표이사")){
            memberEntity.setRole(Role.ADMIN);
        }
        else if(memberDto.getPositionEntity().getPositionName().equals("부장")){
            memberEntity.setRole(Role.MANAGER);
        }
        else if(memberDto.getPositionEntity().getPositionName().equals("사원")){
            memberEntity.setRole(Role.MEMBER);
        }
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setMemberAttachFile(1);
        memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());
        memberEntity.setMemberFileName(memberDto.getMemberFileName());
        return memberEntity;

    }


    public static MemberEntity toMemberUpdateEntity0(MemberDto memberDto) {
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberFileName(memberDto.getMemberFileName());
        memberEntity.setName(memberDto.getName());
        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserPw(memberDto.getUserPw());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setEmployeeDate(memberDto.getEmployeeDate());
        memberEntity.setResignationDate(memberDto.getResignationDate());
        memberEntity.setRole(memberDto.getRole());
        memberEntity.setPhone(memberDto.getPhone());
        if (memberDto.getDepartmentEntity() != null) memberEntity.setDepartmentEntity(memberDto.getDepartmentEntity());
        if (memberDto.getPositionEntity() != null)memberEntity.setPositionEntity(memberDto.getPositionEntity());
        memberEntity.setMemberAttachFile(0);
        memberEntity.setMemberFileName(memberDto.getMemberFileName());
        memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());
        return memberEntity;
    }

    public static MemberEntity toMemberUpdateEntity1(MemberDto memberDto) {
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberFileName(memberDto.getMemberFileName());
        memberEntity.setName(memberDto.getName());
        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserPw(memberDto.getUserPw());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setEmployeeDate(memberDto.getEmployeeDate());
        memberEntity.setResignationDate(memberDto.getResignationDate());
        memberEntity.setRole(memberDto.getRole());
        memberEntity.setPhone(memberDto.getPhone());
        if (memberDto.getDepartmentEntity() != null) memberEntity.setDepartmentEntity(memberDto.getDepartmentEntity());
        if (memberDto.getPositionEntity() != null)memberEntity.setPositionEntity(memberDto.getPositionEntity());
        memberEntity.setMemberAttachFile(1);
        if (memberDto.getMemberFileName() != null)memberEntity.setMemberFileName(memberDto.getMemberFileName());
        memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());
        return memberEntity;
    }


}
