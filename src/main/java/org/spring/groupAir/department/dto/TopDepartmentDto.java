package org.spring.groupAir.department.dto;

import lombok.*;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.department.entity.TopDepartmentEntity;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopDepartmentDto{

    private Long id;

    @NotBlank(message = "부서명을 입력하세요!")
    private String topDepartmentName;

    private List<DepartmentEntity> departmentEntityList;

    public static TopDepartmentDto toDepartmentDto(TopDepartmentEntity topDepartmentEntity) {

        TopDepartmentDto topDepartmentDto = new TopDepartmentDto();

        topDepartmentDto.setId(topDepartmentEntity.getId());
        topDepartmentDto.setTopDepartmentName(topDepartmentEntity.getTopDepartmentName());
        topDepartmentDto.setDepartmentEntityList(topDepartmentEntity.getDepartmentEntityList());

        return topDepartmentDto;

    }


}
