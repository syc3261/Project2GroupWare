package org.spring.groupAir.schedule.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.airplane.entity.AirPlaneEntity;
import org.spring.groupAir.airplane.entity.QAirPlaneEntity;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.schedule.dto.ScheduleDto;
import org.spring.groupAir.schedule.entity.QScheduleEntity;
import org.spring.groupAir.schedule.entity.ScheduleEntity;
import org.spring.groupAir.schedule.repository.ScheduleRepository;
import org.spring.groupAir.schedule.service.scheduleInterface.ScheduleInterface;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
@RequiredArgsConstructor
public class ScheduleService implements ScheduleInterface {

  private final ScheduleRepository scheduleRepository;
  private final JPAQueryFactory queryFactory;


  public List<ScheduleDto> scheduleListAll() {


    List<ScheduleDto> scheduleDtoList = new ArrayList<>();
    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAll();


    for (ScheduleEntity entity : scheduleEntities) {
      ScheduleDto scheduleDto = ScheduleDto.builder()

          .id(entity.getId())
          .employeeId(entity.getMemberEntity().getId())
          .content(entity.getContent())
          .start(entity.getStart())
          .end(entity.getEnd())
          .memberEntity(entity.getMemberEntity())
          .build();


      scheduleDtoList.add(scheduleDto);
    }

    return scheduleDtoList;
  }

  public void setCalendar(ScheduleDto scheduleDto) {


    System.out.println(scheduleDto + "  <<< scheduleDto ");
    System.out.println(scheduleDto.getEmployeeId() + "  <<<   scheduleDto.getEmployeeId() ");


    scheduleDto.setMemberEntity(MemberEntity.builder().id(scheduleDto.getEmployeeId()).build());

    ScheduleEntity entity = ScheduleEntity
        .builder()
//        .title(scheduleDto.getContent())


        .content(scheduleDto.getContent())
        .start(scheduleDto.getStart())
        .end(scheduleDto.getEnd())
        .memberEntity(scheduleDto.getMemberEntity())
        .build();

    System.out.println(entity.getEnd() + "  <<< getEnd ");
    System.out.println(entity.getStart() + "  <<< scheduleDto ");

    ScheduleEntity scheduleEntity = scheduleRepository.save(entity);

  }

  //----------------------------------------------------------//
  @Override
  public List<ScheduleDto> mySchedule(Long id) {


    List<ScheduleEntity> scheduleEntityList = scheduleRepository.findByMemberEntityId(id);


    List<ScheduleDto> scheduleDtoList = scheduleEntityList.stream().map(scheduleEntity ->
        ScheduleDto.builder()
            .id(scheduleEntity.getId())
            .memberEntity(scheduleEntity.getMemberEntity())
            .content(scheduleEntity.getContent())
            .employeeId(scheduleEntity.getMemberEntity().getId())
            .start(scheduleEntity.getStart())
            .end(scheduleEntity.getEnd())
            .build()).collect(Collectors.toList());


    return scheduleDtoList;
  }

  @Override
  public List<ScheduleDto> getScheduleByEmployeeId(Long id) {
    List<ScheduleEntity> scheduleEntityList = scheduleRepository.findByMemberEntityId(id);

    List<ScheduleDto> scheduleDtoList = scheduleEntityList.stream()
        .map(scheduleEntity -> ScheduleDto.builder()
            .id(scheduleEntity.getId())
            .memberEntity(scheduleEntity.getMemberEntity())
            .employeeId(scheduleEntity.getMemberEntity().getId())
            .content(scheduleEntity.getContent())
            .start(scheduleEntity.getStart())
            .end(scheduleEntity.getEnd())
            .build())
        .collect(Collectors.toList());

    return scheduleDtoList;

  }

  @Override
  public List<ScheduleDto> todayAllSchedule() {

    QScheduleEntity schedule = QScheduleEntity.scheduleEntity;

// 현재 날짜를 가져오기
    LocalDate today = LocalDate.now();

    List<ScheduleEntity> scheduleEntityList = queryFactory.selectFrom(schedule)
        .where(
            // 시작일과 종료일이 오늘의 날짜 사이에 있는 경우
            Expressions.stringTemplate("date_format({0}, '%Y-%m-%d')", schedule.start)
                .loe(today.toString())
                .and(
                    Expressions.stringTemplate("date_format({0}, '%Y-%m-%d')", schedule.end)
                        .goe(today.toString())
                )
        )
        .fetch();

    List<ScheduleDto> scheduleDtoList = scheduleEntityList.stream()
        .map(scheduleEntity -> ScheduleDto.builder()
            .id(scheduleEntity.getId())
            .memberEntity(scheduleEntity.getMemberEntity())
            .employeeId(scheduleEntity.getMemberEntity().getId())
            .content(scheduleEntity.getContent())
            .start(scheduleEntity.getStart())
            .end(scheduleEntity.getEnd())
            .build())
        .collect(Collectors.toList());

    return scheduleDtoList;
  }

  @Override
  public List<ScheduleDto> todayMySchedule(Long id) {
    QScheduleEntity schedule = QScheduleEntity.scheduleEntity;

// 현재 날짜를 가져오기
    LocalDate today = LocalDate.now();

    List<ScheduleEntity> scheduleEntityList = queryFactory.selectFrom(schedule)
        .where(
            // 시작일과 종료일이 오늘의 날짜 사이에 있는 경우
            Expressions.stringTemplate("date_format({0}, '%Y-%m-%d')", schedule.start)
                .loe(today.toString())
                .and(
                    Expressions.stringTemplate("date_format({0}, '%Y-%m-%d')", schedule.end)
                        .goe(today.toString())

                ).and(schedule.memberEntity.id.eq(id))
        )
        .fetch();

    List<ScheduleDto> scheduleDtoList = scheduleEntityList.stream()
        .map(scheduleEntity -> ScheduleDto.builder()
            .id(scheduleEntity.getId())
            .memberEntity(scheduleEntity.getMemberEntity())
            .employeeId(scheduleEntity.getMemberEntity().getId())
            .content(scheduleEntity.getContent())
            .start(scheduleEntity.getStart())
            .end(scheduleEntity.getEnd())
            .build())
        .collect(Collectors.toList());


    return scheduleDtoList;
  }



  @Override
    public void deleteSchedule(Long id) {
         scheduleRepository.findById(id).orElseThrow(IllegalArgumentException::new);

         scheduleRepository.deleteById(id);
    }


}