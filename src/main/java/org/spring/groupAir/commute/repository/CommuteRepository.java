package org.spring.groupAir.commute.repository;

import org.spring.groupAir.commute.entity.CommuteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Repository
public interface CommuteRepository extends JpaRepository<CommuteEntity, Long> {

    List<CommuteEntity> findByMemberEntityId(Long id);
//
//    @Query(value = "SELECT count(*) FROM commute c LEFT JOIN employee e ON c.employee_id = e.employee_id WHERE c.in_Time > :date AND c.status = '퇴근' AND c.commute_id IN (SELECT MAX(c2.commute_id) FROM commute c2 GROUP BY c2.employee_id )", nativeQuery = true)
//    int findByWorkOutPeople(@Param("date") LocalDate now);
//
//    @Query(value = "SELECT count(*) FROM commute c LEFT JOIN employee e ON c.employee_id = e.employee_id WHERE c.in_Time > :date AND c.status = '출근' AND c.commute_id IN (SELECT MAX(c2.commute_id) FROM commute c2 GROUP BY c2.employee_id )", nativeQuery = true)
//    int findByWorkPeople(@Param("date") LocalDate now);
//
//    @Query(value = "SELECT count(*) FROM commute c LEFT JOIN employee e ON c.employee_id = e.employee_id WHERE c.in_Time > :date AND c.status = '조퇴' AND c.commute_id IN (SELECT MAX(c2.commute_id) FROM commute c2 GROUP BY c2.employee_id )", nativeQuery = true)
//    int findByLeaveEarlyPeople(@Param("date") LocalDate now);

//    @Query(value = "SELECT count(*) FROM commute c LEFT JOIN employee e ON c.employee_id = e.employee_id WHERE c.in_Time > :date AND c.status = '지각' AND c.commute_id IN (SELECT MAX(c2.commute_id) FROM commute c2 GROUP BY c2.employee_id )", nativeQuery = true)
//    int findByLatePeople(@Param("date") LocalDate now);

//    @Query(value = "SELECT count(*) FROM commute c LEFT JOIN employee e ON c.employee_id = e.employee_id WHERE c.status = '미출근' AND c.commute_id IN (SELECT MAX(c2.commute_id) FROM commute c2 GROUP BY c2.employee_id )", nativeQuery = true)
//    int findByNotWorkInPeople(LocalDate now);


//    @Query(value = "SELECT * FROM commute c LEFT JOIN employee e ON c.employee_id = e.employee_id WHERE c.commute_id IN (SELECT MAX(c2.commute_id) FROM commute c2 GROUP BY c2.employee_id )", nativeQuery = true)
//    List<CommuteEntity> findNotWorkOutPeople();
//    @Query(value = "SELECT * FROM commute c LEFT JOIN employee e ON c.employee_id = e.employee_id WHERE c.commute_id IN (SELECT MAX(c2.commute_id) FROM commute c2 GROUP BY c2.employee_id )", nativeQuery = true)
//    List<CommuteEntity> findNotWorkInPeople();

    @Query(value = "SELECT SUM(total_work) " +
        "FROM commute WHERE employee_id = :id AND MONTH(in_time) = :month " +
        "AND MONTH(out_time) = :month", nativeQuery = true)
    Long findSumTotalWork(@Param("id") Long id, @Param("month") int month);


}
