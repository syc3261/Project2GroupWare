package org.spring.groupAir.salary.service;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.commute.entity.CommuteEntity;
import org.spring.groupAir.commute.entity.QCommuteEntity;
import org.spring.groupAir.commute.repository.CommuteRepository;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.QMemberEntity;
import org.spring.groupAir.member.repository.MemberRepository;
import org.spring.groupAir.salary.dto.SalaryDto;
import org.spring.groupAir.salary.entity.QSalaryEntity;
import org.spring.groupAir.salary.entity.SalaryEntity;
import org.spring.groupAir.salary.repository.SalaryRepository;
import org.spring.groupAir.salary.service.serviceInterface.SalaryServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Service
@Transactional
@RequiredArgsConstructor
public class SalaryService implements SalaryServiceInterface {

    private final MemberRepository memberRepository;
    private final CommuteRepository commuteRepository;
    private final SalaryRepository salaryRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public void createSalary(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        LocalDate paymentDate = YearMonth.now().atEndOfMonth();

        if (memberEntity.getPositionEntity().getPositionName().equals("부장")) {
            SalaryEntity salaryEntity = SalaryEntity
                .builder()
                .paymentDate(paymentDate)
                .pay(5000000)
                .totalPay(5000000)
                .memberEntity(memberEntity)
                .build();

            salaryRepository.save(salaryEntity);
        } else if (memberEntity.getPositionEntity().getPositionName().equals("사원")) {
            SalaryEntity salaryEntity = SalaryEntity
                .builder()
                .paymentDate(paymentDate)
                .pay(2060000)
                .totalPay(2060000)
                .memberEntity(memberEntity)
                .build();
            salaryRepository.save(salaryEntity);
        } else {
            SalaryEntity salaryEntity = SalaryEntity
                .builder()
                .paymentDate(paymentDate)
                .pay(0)
                .totalPay(0)
                .memberEntity(memberEntity)
                .build();
            salaryRepository.save(salaryEntity);
        }
    }

    @Override
    public Page<SalaryDto> memberSalary(Pageable pageable) {

//        Page<SalaryEntity> salaryEntityPage = salaryRepository.findLastMonthSalaryPageList(pageable);

        QSalaryEntity salary = QSalaryEntity.salaryEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        QueryResults<SalaryEntity> salaryEntityResults = queryFactory.select(salary)
            .from(salary)
            .leftJoin(salary.memberEntity, member)
            .where(salary.id.in(JPAExpressions.select(salary.id.max())
                    .from(salary)
                    .groupBy(salary.memberEntity.id)))
             .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        Page<SalaryEntity> salaryEntityPage = new PageImpl<>(salaryEntityResults.getResults(), pageable, salaryEntityResults.getTotal());

        Page<SalaryDto> salaryDtoPage = salaryEntityPage.map(salaryEntity ->
            SalaryDto.builder()
                .id(salaryEntity.getId())
                .memberEntity(salaryEntity.getMemberEntity())
                .pay(salaryEntity.getPay())
                .incentive(salaryEntity.getIncentive())
                .totalPay(salaryEntity.getPay() + salaryEntity.getIncentive())
                .paymentDate(salaryEntity.getPaymentDate())
                .build()
        );

        return salaryDtoPage;
    }

    @Override
    public SalaryDto updateSalary(Long id) {

        SalaryEntity salaryEntity = salaryRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        SalaryDto salaryDto = SalaryDto.builder()
            .id(salaryEntity.getId())
            .pay(salaryEntity.getPay())
            .paymentDate(salaryEntity.getPaymentDate())
            .incentive(salaryEntity.getIncentive())
            .totalPay(salaryEntity.getIncentive() + salaryEntity.getPay())
            .memberEntity(salaryEntity.getMemberEntity())
            .createTime(salaryEntity.getCreateTime())
            .updateTime(salaryEntity.getUpdateTime())
            .build();

        return salaryDto;
    }

    @Override
    public void update(SalaryDto salaryDto) {
        SalaryEntity salaryEntity =
            salaryRepository.findById(salaryDto.getId()).orElseThrow(IllegalArgumentException::new);

        salaryDto.setMemberEntity(MemberEntity.builder().id(salaryDto.getEmployeeId()).build());

        salaryEntity = SalaryEntity.builder()
            .id(salaryDto.getId())
            .pay(salaryDto.getPay())
            .incentive(salaryDto.getIncentive())
            .totalPay(salaryDto.getPay() + salaryDto.getIncentive())
            .paymentDate(salaryDto.getPaymentDate())
            .memberEntity(salaryDto.getMemberEntity())
            .build();

        salaryRepository.save(salaryEntity);
    }

    @Override
    public void overWork(Long id) {
        CommuteEntity commuteEntity = commuteRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Long allTotalWork = (long) (commuteRepository.findSumTotalWork(commuteEntity.getMemberEntity().getId(), LocalDate.now().getMonth().getValue()) / Math.pow(10, 9));

        LocalDate paymentDate = YearMonth.now().atEndOfMonth();

        Duration totalWorkDuration = (allTotalWork != null)
            ? Duration.ofSeconds(allTotalWork)
            : Duration.ZERO;

        if (totalWorkDuration.toHours() >= 10) {
            if (commuteEntity.getMemberEntity().getPositionEntity().getPositionName().equals("부장")) {
                int overWorkSalary = (int) ((totalWorkDuration.toHours() - 10) * 30000);

//                SalaryEntity salaryEntity = salaryRepository.findLastMonthSalary(commuteEntity.getMemberEntity().getId());

                QSalaryEntity salary = QSalaryEntity.salaryEntity;
                QMemberEntity member = QMemberEntity.memberEntity;

                SalaryEntity salaryEntity = queryFactory.select(salary)
                    .from(salary)
                    .leftJoin(salary.memberEntity, member)
                    .where(member.id.eq(commuteEntity.getMemberEntity().getId())
                        .and(salary.id.in(JPAExpressions.select(salary.id.max())
                            .from(salary)
                            .groupBy(salary.memberEntity.id))))
                    .fetchOne();

                salaryEntity.setIncentive(overWorkSalary);

                SalaryEntity salaryEntity1 = SalaryEntity.builder()
                    .id(salaryEntity.getId())
                    .memberEntity(commuteEntity.getMemberEntity())
                    .pay(salaryEntity.getPay())
                    .incentive(salaryEntity.getIncentive())
                    .totalPay(salaryEntity.getPay() + salaryEntity.getIncentive())
                    .paymentDate(paymentDate)
                    .build();

                salaryRepository.save(salaryEntity1);

            } else if (commuteEntity.getMemberEntity().getPositionEntity().getPositionName().equals("사원")) {

                int overWorkSalary = (int) ((totalWorkDuration.toHours() - 10) * 15000);

                QSalaryEntity salary = QSalaryEntity.salaryEntity;
                QMemberEntity member = QMemberEntity.memberEntity;

                SalaryEntity salaryEntity = queryFactory.select(salary)
                    .from(salary)
                    .leftJoin(salary.memberEntity, member)
                    .where(member.id.eq(commuteEntity.getMemberEntity().getId())
                        .and(salary.id.in(JPAExpressions.select(salary.id.max())
                            .from(salary)
                            .groupBy(salary.memberEntity.id))))
                    .fetchOne();

                salaryEntity.setIncentive(overWorkSalary);

                SalaryEntity salaryEntity1 = SalaryEntity.builder()
                    .id(salaryEntity.getId())
                    .memberEntity(commuteEntity.getMemberEntity())
                    .pay(salaryEntity.getPay())
                    .incentive(salaryEntity.getIncentive())
                    .totalPay(salaryEntity.getPay() + salaryEntity.getIncentive())
                    .paymentDate(paymentDate)
                    .build();

                salaryRepository.save(salaryEntity1);
            }
        }
    }

    @Override
    public void updateSalaryDate() {
        QSalaryEntity salary = QSalaryEntity.salaryEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        List<SalaryEntity> salaryEntityList = queryFactory.select(salary)
            .from(salary)
            .leftJoin(salary.memberEntity, member)
            .where(salary.id.in(JPAExpressions.select(salary.id.max())
                .from(salary)
                .groupBy(salary.memberEntity.id)))
            .fetch();

        LocalDate paymentDate = YearMonth.now().atEndOfMonth();
        LocalDate now = LocalDate.now();
        for (SalaryEntity salaryEntity : salaryEntityList) {
            if (salaryEntity.getPaymentDate().isBefore(now)) {
                if (salaryEntity.getMemberEntity().getPositionEntity().getPositionName().equals("부장")) {
                    salaryEntity = SalaryEntity
                        .builder()
                        .paymentDate(paymentDate)
                        .pay(salaryEntity.getPay())
                        .totalPay(salaryEntity.getPay())
                        .memberEntity(salaryEntity.getMemberEntity())
                        .build();

                    salaryRepository.save(salaryEntity);
                } else if (salaryEntity.getMemberEntity().getPositionEntity().getPositionName().equals("사원")) {
                    salaryEntity = SalaryEntity
                        .builder()
                        .paymentDate(paymentDate)
                        .pay(salaryEntity.getPay())
                        .totalPay(salaryEntity.getPay())
                        .memberEntity(salaryEntity.getMemberEntity())
                        .build();
                    salaryRepository.save(salaryEntity);
                } else {
                    salaryEntity = SalaryEntity
                        .builder()
                        .paymentDate(paymentDate)
                        .pay(salaryEntity.getPay())
                        .totalPay(0)
                        .memberEntity(salaryEntity.getMemberEntity())
                        .build();
                    salaryRepository.save(salaryEntity);
                }
            }
        }
    }

    @Override
    public List<SalaryDto> mySalary(Long id) {

        List<SalaryEntity> salaryEntityList = salaryRepository.findByMemberEntityId(id);

        List<SalaryDto> salaryDtoList = salaryEntityList.stream().map(salaryEntity ->
            SalaryDto.builder()
                .id(salaryEntity.getId())
                .memberEntity(salaryEntity.getMemberEntity())
                .pay(salaryEntity.getPay())
                .incentive(salaryEntity.getIncentive())
                .totalPay(salaryEntity.getPay() + salaryEntity.getIncentive())
                .paymentDate(salaryEntity.getPaymentDate())
                .build()).collect(Collectors.toList());

        return salaryDtoList;
    }
}
