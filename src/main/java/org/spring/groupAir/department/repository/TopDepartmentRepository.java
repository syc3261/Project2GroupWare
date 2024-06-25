package org.spring.groupAir.department.repository;

import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.department.entity.TopDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopDepartmentRepository extends JpaRepository<TopDepartmentEntity, Long> {

    // @Query 사용시
//    @Query("SELECT td FROM TopDepartmentEntity td JOIN td.departmentEntityList d JOIN d.memberEntityList m WHERE m.id = :id")
//    List<TopDepartmentEntity> findTopDepartmentByEmployeeId(@Param("id") Long id);

    // @Query 사용안함
    List<TopDepartmentEntity> findByDepartmentEntityListMemberEntityListId(Long id);

}


