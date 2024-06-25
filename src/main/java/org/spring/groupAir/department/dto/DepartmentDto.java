package org.spring.groupAir.department.dto;

import lombok.*;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.department.entity.TopDepartmentEntity;
import org.spring.groupAir.member.entity.MemberEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {

    private Long id;

    @NotBlank(message = "부서명을 입력하세요!")
    private String departmentName;

    private List<MemberEntity> memberEntityList;

    @NotNull(message = "상위부서를 선택하세요!")
    private TopDepartmentEntity topDepartmentEntity;


    public static DepartmentEntity toWriteDeEntity(DepartmentDto departmentDto) {

//        departmentDto.topDepartmentEntity.TopDepartmentName

        DepartmentEntity departmentEntity = new DepartmentEntity();

        departmentEntity.setId(departmentDto.getId());
        departmentEntity.setDepartmentName(departmentDto.getDepartmentName());
        departmentEntity.setTopDepartmentEntity(departmentDto.getTopDepartmentEntity());
        departmentEntity.setMemberEntityList(departmentDto.getMemberEntityList());

        return departmentEntity;
    }

    public static DepartmentDto fromEntity(DepartmentEntity departmentEntity) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(departmentEntity.getId());
        departmentDto.setDepartmentName(departmentEntity.getDepartmentName());
        departmentDto.setTopDepartmentEntity(departmentEntity.getTopDepartmentEntity());
        departmentDto.setMemberEntityList(departmentEntity.getMemberEntityList());
        // 다른 필드들도 필요에 따라 설정

        return departmentDto;
    }
}
