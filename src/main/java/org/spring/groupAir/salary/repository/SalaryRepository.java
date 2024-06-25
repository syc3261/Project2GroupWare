package org.spring.groupAir.salary.repository;

import org.spring.groupAir.salary.entity.SalaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {
    List<SalaryEntity> findByMemberEntityId(Long id);

//
//    @Query(value = "SELECT * FROM salary s LEFT JOIN employee e ON s.employee_id = e.employee_id WHERE s.salary_id IN (SELECT MAX(s2.salary_id) FROM salary s2 GROUP BY s2.employee_id) ", nativeQuery = true)
//    List<SalaryEntity> findLastMonthSalaryList();
//    @Query(value = "SELECT * FROM salary s LEFT JOIN employee e ON s.employee_id = e.employee_id WHERE e.employee_id = :id and s.salary_id IN (SELECT MAX(s2.salary_id) FROM salary s2 GROUP BY s2.employee_id) ", nativeQuery = true)
//    SalaryEntity findLastMonthSalary(@Param("id") Long id);
//    @Query(value = "SELECT s FROM SalaryEntity s LEFT JOIN MemberEntity m ON s.memberEntity.id = m.id WHERE s.id IN (SELECT MAX(s2.id) FROM SalaryEntity s2 GROUP BY s2.memberEntity.id) order by s.id desc")
//    Page<SalaryEntity> findLastMonthSalaryPageList(Pageable pageable);
}
