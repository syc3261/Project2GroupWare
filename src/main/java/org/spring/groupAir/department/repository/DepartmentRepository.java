package org.spring.groupAir.department.repository;

import org.spring.groupAir.department.dto.DepartmentDto;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.department.entity.TopDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    List<DepartmentEntity> findByTopDepartmentEntityId(Long topDepartmentId);
}
