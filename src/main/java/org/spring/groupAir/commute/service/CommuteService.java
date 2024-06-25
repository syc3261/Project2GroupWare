package org.spring.groupAir.commute.service;

import antlr.TokenStreamSelector;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.commute.dto.CommuteDto;
import org.spring.groupAir.commute.entity.CommuteEntity;
import org.spring.groupAir.commute.entity.QCommuteEntity;
import org.spring.groupAir.commute.repository.CommuteRepository;
import org.spring.groupAir.commute.service.serviceInterface.CommuteServiceInterface;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.QMemberEntity;
import org.spring.groupAir.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommuteService implements CommuteServiceInterface {

    private final CommuteRepository commuteRepository;
    private final MemberRepository memberRepository;
    private final JPAQueryFactory queryFactory;


    @Override
    public Long workIn(Long id) {
        CommuteEntity commuteEntity1 = commuteRepository.findById(id).get();
        Long memberId = commuteEntity1.getMemberEntity().getId();
        if (commuteEntity1.getInTime() == null) {
            CommuteEntity commuteEntity2 = CommuteEntity
                .builder()
                .id(id)
                .work(1)
                .status(LocalDateTime.now().getHour() >= 16 ? "지각" : "출근")
                .inTime(LocalDateTime.now())
                .memberEntity(commuteEntity1.getMemberEntity()).build();
            commuteRepository.save(commuteEntity2);
        } else {
            CommuteEntity commuteEntity2 = CommuteEntity
                .builder()
                .work(1)
                .status(LocalDateTime.now().getHour() >= 16 ? "지각" : "출근")
                .inTime(LocalDateTime.now())
                .memberEntity(commuteEntity1.getMemberEntity()).build();
            commuteRepository.save(commuteEntity2);
        }

        return memberId;
    }

    @Override
    public Long workOut(Long id) {
        CommuteEntity commuteEntity1 = commuteRepository.findById(id).get();
        Long memberId = commuteEntity1.getMemberEntity().getId();

        if (commuteEntity1.getInTime() != null && commuteEntity1.getOutTime() == null) {
            Duration totalWork = Duration.between(commuteEntity1.getInTime(), LocalDateTime.now());
            String status = totalWork.toMinutes() > 1 ? "퇴근" : "조퇴";
            CommuteEntity commuteEntity2 = CommuteEntity
                .builder()
                .id(id)
                .work(0)
                .status(status)
                .inTime(commuteEntity1.getInTime())
                .outTime(LocalDateTime.now())
                .totalWork(totalWork)
                .memberEntity(commuteEntity1.getMemberEntity()).build();
            commuteRepository.save(commuteEntity2);
        } else {
            Duration totalWork = Duration.between(commuteEntity1.getInTime(), LocalDateTime.now());
            CommuteEntity commuteEntity2 = CommuteEntity
                .builder()
                .work(0)
                .status("퇴근")
                .inTime(commuteEntity1.getInTime())
                .outTime(LocalDateTime.now())
                .totalWork(totalWork)
                .memberEntity(commuteEntity1.getMemberEntity()).build();
            commuteRepository.save(commuteEntity2);
        }

        return memberId;
    }

    @Override
    public List<CommuteDto> commuteList(Long id) {
        List<CommuteEntity> commuteEntityList = commuteRepository.findByMemberEntityId(id);

        List<CommuteDto> commuteDtoList =
            commuteEntityList.stream().map(CommuteDto::toCommuteDto).collect(Collectors.toList());

        return commuteDtoList;
    }

    @Override
    public void createCommute(Long id) {
        MemberEntity memberEntity =
            memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        CommuteEntity commuteEntity = CommuteEntity
            .builder()
            .status("미출근")
            .memberEntity(memberEntity)
            .build();
        commuteRepository.save(commuteEntity);
    }

    @Override
    public Page<CommuteDto> commutePageList(Pageable pageable) {

        Page<CommuteEntity> commuteEntityPage = commuteRepository.findAll(pageable);

        Page<CommuteDto> commuteDtoPage = commuteEntityPage.map(CommuteDto::toCommuteDto);

        return commuteDtoPage;
    }

    @Override
    public int latePeople() {

        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        LocalDate now = LocalDate.now();

        int latePeople = (int) queryFactory.select(commute.count())
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.inTime.gt(now.atStartOfDay())
                .and(commute.status.eq("지각"))
                .and(commute.id.in(JPAExpressions.select(commute.id.max())
                    .from(commute)
                    .groupBy(commute.memberEntity.id))))
            .fetchCount();

        return latePeople;
    }

    @Override
    public int leaveEarlyPeople() {
        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        LocalDate now = LocalDate.now();

        int leaveEarlyPeople = (int) queryFactory.select(commute.count())
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.inTime.gt(now.atStartOfDay())
                .and(commute.status.eq("조퇴"))
                .and(commute.id.in(JPAExpressions.select(commute.id.max())
                    .from(commute)
                    .groupBy(commute.memberEntity.id))))
            .fetchCount();

        return leaveEarlyPeople;
    }

    @Override
    public int workPeople() {
        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        LocalDate now = LocalDate.now();

        int workInPeople = (int) queryFactory
            .select(commute.count())
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.inTime.gt(now.atStartOfDay())
                .and(commute.status.eq("출근"))
                .and(commute.id.in(
                    JPAExpressions
                        .select(commute.id.max())
                        .from(commute)
                        .groupBy(commute.memberEntity.id))))
            .fetchCount();

        return workInPeople;
    }

    @Override
    public int workOutPeople() {

        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        LocalDate now = LocalDate.now();

        int workOutPeople = (int) queryFactory.select(commute.count())
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.inTime.gt(now.atStartOfDay())
                .and(commute.status.eq("퇴근"))
                .and(commute.id.in(JPAExpressions.select(commute.id.max())
                    .from(commute)
                    .groupBy(commute.memberEntity.id))))
            .fetchCount();

        return workOutPeople;
    }

    @Override
    public int notWorkInPeople() {

        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        int notWorkInPeople = (int) queryFactory.select(commute.count())
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.status.eq("미출근")
                .and(commute.id.in(JPAExpressions.select(commute.id.max())
                    .from(commute)
                    .groupBy(commute.memberEntity.id))))
            .fetchCount();

        return notWorkInPeople;
    }

    @Override
    public Duration totalWork(Long id) {
        int month = LocalDate.now().getMonth().getValue();
        Long allTotalWork = commuteRepository.findSumTotalWork(id, month);

        if (allTotalWork == null) {
            allTotalWork = 0L;
        } else {
            allTotalWork = allTotalWork / (long) Math.pow(10, 9);
        }

        // Long 값을 Duration으로 변환
        Duration totalWorkDuration = Duration.ofSeconds(allTotalWork);

        return totalWorkDuration;
    }

    @Override
    public void notWorkOut() {

        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        List<CommuteEntity> commuteEntityList = queryFactory.select(commute)
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.id.in(JPAExpressions.select(commute.id.max())
                .from(commute)
                .groupBy(commute.memberEntity.id))).fetch();

        for (CommuteEntity commuteEntity : commuteEntityList) {
            if (commuteEntity.getInTime() != null
                && !commuteEntity.getInTime().toLocalDate().isEqual(LocalDate.now())
                && commuteEntity.getOutTime() == null) {
                commuteRepository.deleteById(commuteEntity.getId());
            }
        }
    }

    @Override
    public void notWorkIn() {

        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        List<CommuteEntity> commuteEntityList = queryFactory.select(commute)
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.id.in(JPAExpressions.select(commute.id.max())
                .from(commute)
                .groupBy(commute.memberEntity.id))).fetch();

        for (CommuteEntity commuteEntity : commuteEntityList) {
            if (!commuteEntity.getStatus().equals("휴가")) {
                if (commuteEntity.getInTime() != null
                    && !commuteEntity.getInTime().toLocalDate()
                    .isEqual(LocalDate.now())) {
                    commuteEntity.setStatus("미출근");
                    commuteRepository.save(commuteEntity);
                } else if (commuteEntity.getInTime() == null) {
                    commuteEntity.setStatus("미출근");
                    commuteRepository.save(commuteEntity);
                }
            }
        }
    }
}

