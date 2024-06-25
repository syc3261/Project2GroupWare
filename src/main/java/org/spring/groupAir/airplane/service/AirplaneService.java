package org.spring.groupAir.airplane.service;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.airplane.dto.AirplaneDto;
import org.spring.groupAir.airplane.entity.AirPlaneEntity;

import org.spring.groupAir.airplane.entity.QAirPlaneEntity;
import org.spring.groupAir.airplane.repository.AirplaneRepository;
import org.spring.groupAir.airplane.service.serviceInterface.AirPlaneServiceInterface;
import org.spring.groupAir.member.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class AirplaneService implements AirPlaneServiceInterface {

    private final AirplaneRepository airplaneRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AirplaneDto> allAirplane(Pageable pageable, String subject, String search) {

        Page<AirPlaneEntity> airPlaneEntityPage;

        if (subject == null || search == null) {
            airPlaneEntityPage = airplaneRepository.findAll(pageable);
        } else if (subject.equals("fromArea")) {
            airPlaneEntityPage = airplaneRepository.findByFromAreaContains(pageable, search);
        } else if (subject.equals("toArea")) {
            airPlaneEntityPage = airplaneRepository.findByToAreaContains(pageable, search);
        } else if (subject.equals("airplane")) {
            airPlaneEntityPage = airplaneRepository.findByAirplaneContains(pageable, search);
        } else {
            airPlaneEntityPage = airplaneRepository.findAll(pageable);
        }

        Page<AirplaneDto> airplaneDtoPage = airPlaneEntityPage.map(airPlaneEntity ->
            AirplaneDto.builder()
                .id(airPlaneEntity.getId())
                .fromTime(airPlaneEntity.getFromTime())
                .fromArea(airPlaneEntity.getFromArea())
                .toArea(airPlaneEntity.getToArea())
                .toTime(airPlaneEntity.getToTime())
                .timeTaken(airPlaneEntity.getTimeTaken())
                .airplane(airPlaneEntity.getAirplane())
                .status(airPlaneEntity.getStatus())
                .memberEntity(airPlaneEntity.getMemberEntity())
                .build()
        );

        return airplaneDtoPage;
    }

    @Override
    public void addAirplane(AirplaneDto airplaneDto) {

        LocalDateTime toTime = airplaneDto.getToTime();
        LocalDateTime fromTime = airplaneDto.getFromTime();

        airplaneDto.setMemberEntity(MemberEntity.builder().id(airplaneDto.getMemberId()).build());

        int timeTaken = (int) Math.abs(Duration.between(toTime, fromTime).toHours());

        AirPlaneEntity airPlaneEntity = AirPlaneEntity
            .builder()
            .fromTime(airplaneDto.getFromTime())
            .fromArea(airplaneDto.getFromArea())
            .toArea(airplaneDto.getToArea())
            .toTime(airplaneDto.getToTime())
            .timeTaken(timeTaken)
            .airplane(airplaneDto.getAirplane())
            .status("정상")
            .memberEntity(airplaneDto.getMemberEntity())
            .build();

        airplaneRepository.save(airPlaneEntity);
    }

    @Override
    public AirplaneDto airplaneDetail(Long id) {

        AirPlaneEntity airPlaneEntity = airplaneRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        AirplaneDto airplaneDto = AirplaneDto.builder()
            .id(airPlaneEntity.getId())
            .toArea(airPlaneEntity.getToArea())
            .fromArea(airPlaneEntity.getFromArea())
            .toTime(airPlaneEntity.getToTime())
            .fromTime(airPlaneEntity.getFromTime())
            .airplane(airPlaneEntity.getAirplane())
            .timeTaken(airPlaneEntity.getTimeTaken())
            .status(airPlaneEntity.getStatus())
            .memberEntity(airPlaneEntity.getMemberEntity())
            .build();

        return airplaneDto;
    }

    @Override
    public void airplaneDelete(Long id) {

        airplaneRepository.deleteById(id);

    }

    @Override
    public Page<AirplaneDto> myAirplane(Pageable pageable, Long id) {

        Page<AirPlaneEntity> airPlaneEntityPage
            = airplaneRepository.findByMemberEntityId(pageable, id);

        Page<AirplaneDto> airplaneDtoPage = airPlaneEntityPage.map(airplaneEntity ->
            AirplaneDto.builder()
                .id(airplaneEntity.getId())
                .toArea(airplaneEntity.getToArea())
                .fromArea(airplaneEntity.getFromArea())
                .toTime(airplaneEntity.getToTime())
                .fromTime(airplaneEntity.getFromTime())
                .airplane(airplaneEntity.getAirplane())
                .timeTaken(airplaneEntity.getTimeTaken())
                .status(airplaneEntity.getStatus())
                .memberEntity(airplaneEntity.getMemberEntity())
                .build()
        );

        return airplaneDtoPage;
    }

    @Override
    public Page<AirplaneDto> todayMyAirplane(Pageable pageable, Long id) {

        LocalDate now = LocalDate.now();

        QAirPlaneEntity airPlane = QAirPlaneEntity.airPlaneEntity;

        QueryResults<AirPlaneEntity> airplaneEntityResults =
            queryFactory.select(airPlane)
            .from(airPlane)
            .where(airPlane.toTime.dayOfMonth().eq(now.getDayOfMonth())
                .and(airPlane.memberEntity.id.eq(id)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        Page<AirPlaneEntity> airPlaneEntityPage = new PageImpl<>(airplaneEntityResults.getResults(), pageable, airplaneEntityResults.getTotal());

        Page<AirplaneDto> airplaneDtoPage = airPlaneEntityPage.map(airplaneEntity ->
            AirplaneDto.builder()
                .id(airplaneEntity.getId())
                .toArea(airplaneEntity.getToArea())
                .fromArea(airplaneEntity.getFromArea())
                .toTime(airplaneEntity.getToTime())
                .fromTime(airplaneEntity.getFromTime())
                .airplane(airplaneEntity.getAirplane())
                .timeTaken(airplaneEntity.getTimeTaken())
                .status(airplaneEntity.getStatus())
                .memberEntity(airplaneEntity.getMemberEntity())
                .build()
        );

        return airplaneDtoPage;
    }

    @Override
    public void deleteOverTimeAirplane() {
        LocalDateTime now = LocalDateTime.now();
        airplaneRepository.deleteByFromTimeBefore(now);
    }

    @Override
    public AirplaneDto findAirplane(Long id) {

        AirPlaneEntity airPlaneEntity
            = airplaneRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        AirplaneDto airplaneDto = AirplaneDto.builder()
            .id(airPlaneEntity.getId())
            .toArea(airPlaneEntity.getToArea())
            .fromArea(airPlaneEntity.getFromArea())
            .toTime(airPlaneEntity.getToTime())
            .fromTime(airPlaneEntity.getFromTime())
            .airplane(airPlaneEntity.getAirplane())
            .timeTaken(airPlaneEntity.getTimeTaken())
            .status(airPlaneEntity.getStatus())
            .memberEntity(airPlaneEntity.getMemberEntity())
            .build();

        return airplaneDto;
    }

    @Override
    public void airplaneUpdate(AirplaneDto airplaneDto) {

        airplaneDto.setMemberEntity(MemberEntity.builder().id(airplaneDto.getMemberId()).build());

        AirPlaneEntity airPlaneEntity = AirPlaneEntity
            .builder()
            .id(airplaneDto.getId())
            .fromTime(airplaneDto.getFromTime())
            .fromArea(airplaneDto.getFromArea())
            .toArea(airplaneDto.getToArea())
            .toTime(airplaneDto.getToTime())
            .timeTaken(airplaneDto.getTimeTaken())
            .airplane(airplaneDto.getAirplane())
            .status(airplaneDto.getStatus())
            .memberEntity(airplaneDto.getMemberEntity())
            .build();

        airplaneRepository.save(airPlaneEntity);
    }

    @Override
    public void updateStatus() {

        LocalDateTime now = LocalDateTime.now();

        QAirPlaneEntity airPlane = QAirPlaneEntity.airPlaneEntity;

        List<AirPlaneEntity> airPlaneEntityList =
            queryFactory.select(airPlane)
                .from(airPlane)
                .where(airPlane.toTime.loe(now).and(airPlane.fromTime.goe(now)))
                .fetch();

        for (AirPlaneEntity airPlaneEntity : airPlaneEntityList) {
            if (!airPlaneEntity.getStatus().equals("운행중")) {
                airPlaneEntity.setStatus("운행중");
                airplaneRepository.save(airPlaneEntity);
            }
        }
    }

    @Override
    public int findTodayAirplane() {

        QAirPlaneEntity airPlane = QAirPlaneEntity.airPlaneEntity;
        LocalDate now = LocalDate.now();

        int todayAirplane = (int) queryFactory.select(airPlane.count())
            .from(airPlane)
            .where(airPlane.toTime.dayOfMonth().eq(now.getDayOfMonth()))
            .fetchCount();

        return todayAirplane;
    }

    @Override
    public int findAllAirplane() {

        QAirPlaneEntity airPlane = QAirPlaneEntity.airPlaneEntity;

        int allAirplane = (int) queryFactory.select(airPlane.count())
            .from(airPlane)
            .fetchCount();

        return allAirplane;
    }

    @Override
    public int todayMyAirplaneCount(Long id) {

        LocalDate now = LocalDate.now();

        QAirPlaneEntity airPlane = QAirPlaneEntity.airPlaneEntity;

        int myAirplaneCount = (int) queryFactory.select(airPlane.count())
            .from(airPlane)
            .where(airPlane.toTime.dayOfMonth().eq(now.getDayOfMonth())
                .and(airPlane.memberEntity.id.eq(id)))
            .fetchCount();
        return myAirplaneCount;
    }

    @Override
    public int myAirplanes(Long id) {

        QAirPlaneEntity airPlane = QAirPlaneEntity.airPlaneEntity;

        int myAirplaneCount = (int) queryFactory.select(airPlane.count())
            .from(airPlane)
            .where(airPlane.memberEntity.id.eq(id))
            .fetchCount();

        return myAirplaneCount;
    }
}
