package org.spring.groupAir.department.entity;

import lombok.*;
import org.spring.groupAir.contraint.BaseTimeEntity;
import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.member.entity.MemberEntity;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "topDepartment")
public class TopDepartmentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topDepartment_id")
    private Long id;

    @Column(nullable = false)
    private String topDepartmentName;

    @OneToMany(mappedBy = "topDepartmentEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<DepartmentEntity> departmentEntityList;

    public static TopDepartmentEntity toTopDeEntity(TopDepartmentDto topDepartmentDto) {

        TopDepartmentEntity topDepartmentEntity = new TopDepartmentEntity();

        topDepartmentEntity.setTopDepartmentName(topDepartmentDto.getTopDepartmentName());

        return topDepartmentEntity;

    }

    public static TopDepartmentDto toUpdateTopDe(TopDepartmentEntity topDepartmentEntity) {

        TopDepartmentDto topDepartmentDto = new TopDepartmentDto();

        topDepartmentDto.setId(topDepartmentEntity.getId());
        topDepartmentDto.setTopDepartmentName(topDepartmentEntity.getTopDepartmentName());
        topDepartmentDto.setDepartmentEntityList(topDepartmentEntity.getDepartmentEntityList());

        return topDepartmentDto;
    }
}
