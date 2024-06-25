package org.spring.groupAir.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberFileDto {

    private Long id;

    private String memberNewFile;

    private String memberOldFile;

    private MemberEntity memberEntity;
}
