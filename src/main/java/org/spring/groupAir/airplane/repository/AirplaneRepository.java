package org.spring.groupAir.airplane.repository;

import org.spring.groupAir.airplane.entity.AirPlaneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AirplaneRepository extends JpaRepository<AirPlaneEntity, Long> {
    Page<AirPlaneEntity> findByFromAreaContains(Pageable pageable, String search);

    Page<AirPlaneEntity> findByToAreaContains(Pageable pageable, String search);

    Page<AirPlaneEntity> findByAirplaneContains(Pageable pageable, String search);

    Page<AirPlaneEntity> findByMemberEntityId(Pageable pageable, Long id);

    void deleteByFromTimeBefore(LocalDateTime dateTime);

//    @Query("SELECT a FROM AirPlaneEntity a WHERE DATE(a.toTime) = :date AND a.memberEntity.id = :id ORDER BY a.toTime DESC")
//    Page<AirPlaneEntity> findTodayAirplane(Pageable pageable, @Param("id") Long id, @Param("date") Date date);

//    @Query(value="SELECT * FROM airplane a WHERE a.to_time <= :time AND a.from_time >= :time", nativeQuery = true)
//    List<AirPlaneEntity> updateStatus(@Param("time")LocalDateTime now);
}
