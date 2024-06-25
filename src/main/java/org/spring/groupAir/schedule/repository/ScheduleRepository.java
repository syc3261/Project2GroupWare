package org.spring.groupAir.schedule.repository;

import org.spring.groupAir.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {

  //----------------------------------------------------------//
//  String jpql = "SELECT s FROM Schedule s WHERE s.employee.id = :employeeId";


/*  @Query("SELECT s FROM ScheduleEntity s WHERE s.memberEntity.id = :id")
  List<ScheduleEntity> findByMemberEntityId(@Param("id") Long id);*/

  @Query("SELECT s FROM ScheduleEntity s WHERE s.memberEntity.id = :id")

  List<ScheduleEntity> findByMemberEntityId(@Param("id") Long employeeId);




}
