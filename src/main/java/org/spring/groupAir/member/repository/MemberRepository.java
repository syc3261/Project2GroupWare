package org.spring.groupAir.member.repository;

import org.apache.catalina.User;
import org.spring.groupAir.commute.entity.VacationEntity;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.MemberFileEntity;
import org.spring.groupAir.movie.entity.BoxOfficeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUserEmail(String userEmail);

    Page<MemberEntity> findByNameContains(Pageable pageable, String search);

    Page<MemberEntity> findByUserEmailContains(Pageable pageable, String search);



    //sign추가
    Page<MemberEntity> findByNameContainingIgnoreCase(Pageable pageable, String name);


    Page<MemberEntity> findByPhoneContains(Pageable pageable, String search);


    Optional<MemberEntity> findByName(String name);


//    @Query(value = "SELECT * FROM employee  WHERE employee_id NOT IN(SELECT v.employee_id FROM vacation v WHERE v.vac_start_date <= :date AND v.vac_end_date >= :date AND (v.vac_type = '병가' or v.vac_type = '휴가'))",nativeQuery = true)
//    List<MemberEntity> findNotVacationPerson(@Param("date") LocalDate now);

    Optional<MemberEntity> findByNameEquals(String name);




    List<MemberEntity> findByPositionEntityPositionName(String pilot);

    Page<MemberEntity> findByPositionEntityPositionName(Pageable pageable,String pilot);



    @Query("SELECT u.userEmail FROM MemberEntity u WHERE u.name = :name AND u.phone = :phone")
    String findEmailByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    @Query("SELECT u.userPw FROM MemberEntity u WHERE u.userEmail = :userEmail AND u.name = :name")
    String findUserPwByUserEmailAndName(@Param("userEmail") String userEmail, @Param("name")String name);




//    MemberEntity findByUserEmailAndUserName(String userEmail, String name);

}
