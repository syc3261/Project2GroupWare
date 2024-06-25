package org.spring.groupAir.commute.service;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.commute.dto.VacationDto;
import org.spring.groupAir.commute.entity.CommuteEntity;
import org.spring.groupAir.commute.entity.QVacationEntity;
import org.spring.groupAir.commute.entity.VacationEntity;
import org.spring.groupAir.commute.repository.CommuteRepository;
import org.spring.groupAir.commute.repository.VacationRepository;
import org.spring.groupAir.commute.service.serviceInterface.VacationServiceInterface;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VacationService implements VacationServiceInterface {

    private final CommuteRepository commuteRepository;
    private final MemberRepository memberRepository;
    private final VacationRepository vacationRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public void vacationCreate(VacationDto vacationDto) {

        QVacationEntity vacation = QVacationEntity.vacationEntity;

        LocalDate vacStartDate = vacationDto.getVacStartDate();
        LocalDate vacEndDate = vacationDto.getVacEndDate();
        Long memberId = vacationDto.getEmployeeId();

        List<VacationEntity> overlappingVacations =
            queryFactory.select(vacation)
                .from(vacation)
                .where(vacation.memberEntity.id.eq(memberId)
                    .and(vacation.vacStartDate.loe(vacEndDate))
                    .and(vacation.vacEndDate.goe(vacStartDate))).fetch();

        if (!overlappingVacations.isEmpty()) {
            throw new IllegalArgumentException("이미 등록된 휴가와 겹칩니다.");
        }

        LocalDateTime vacStartDateTime = vacationDto.getVacStartDate().atStartOfDay();
        LocalDateTime vacEndDateTime = vacationDto.getVacEndDate().atStartOfDay();

        int vacDays = (int) Duration.between(vacStartDateTime, vacEndDateTime).toDays();

        vacationDto.setMemberEntity(MemberEntity.builder()
            .id(vacationDto.getEmployeeId())
            .build());

        VacationEntity vacationEntity = VacationEntity.builder()
            .vacDays(vacDays + 1)
            .vacType(vacationDto.getVacType())
            .vacEndDate(vacEndDate)
            .vacStartDate(vacStartDate)
            .memberEntity(vacationDto.getMemberEntity())
            .build();

        vacationRepository.save(vacationEntity);
    }

    @Override
    public int vacationPeople() {

//        int vPeople = vacationRepository.findAllByDate(LocalDate.now());

        QVacationEntity vacation = QVacationEntity.vacationEntity;

        LocalDate now = LocalDate.now();

        int vPeople = (int) queryFactory.select(vacation.count())
            .from(vacation)
            .where(vacation.vacStartDate.loe(now)
                .and(vacation.vacEndDate.goe(now)
                    .and(vacation.vacType.eq("휴가"))))
            .fetchCount();

        return vPeople;
    }

    @Override
    public int sickVacationPeople() {
        QVacationEntity vacation = QVacationEntity.vacationEntity;

        LocalDate now = LocalDate.now();

        int sicVacationPeople = (int) queryFactory.select(vacation.count())
            .from(vacation)
            .where(vacation.vacStartDate.loe(now)
                .and(vacation.vacEndDate.goe(now)
                    .and(vacation.vacType.eq("병가"))))
            .fetchCount();

        return sicVacationPeople;
    }

    @Override
    public void findVacationPerson() {

        QVacationEntity vacation = QVacationEntity.vacationEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        LocalDate now = LocalDate.now();

        List<VacationEntity> vacationEntityList =
            queryFactory.select(vacation)
            .from(vacation)
            .where(vacation.vacStartDate.loe(now)
                .and(vacation.vacEndDate.goe(now))
                .and(vacation.vacType.eq("병가")
                    .or(vacation.vacType.eq("휴가"))))
            .fetch();


        for (VacationEntity vacationEntity : vacationEntityList) {
            List<CommuteEntity> commuteList
                = vacationEntity.getMemberEntity().getCommuteEntityList();
            if (!commuteList.isEmpty() &&
                !vacationEntity.getMemberEntity()
                    .getCommuteEntityList()
                    .get(vacationEntity
                        .getMemberEntity()
                        .getCommuteEntityList().size() - 1)
                    .getStatus()
                    .equals("휴가")) {
                CommuteEntity commuteEntity = CommuteEntity.builder()
                    .id(vacationEntity
                        .getMemberEntity()
                        .getCommuteEntityList()
                        .get(vacationEntity
                            .getMemberEntity()
                            .getCommuteEntityList().size() - 1)
                        .getId())
                    .work(0)
                    .status("휴가")
                    .memberEntity(vacationEntity.getMemberEntity())
                    .build();

                commuteRepository.save(commuteEntity);
            }
        }

        List<MemberEntity> memberEntityList =
            queryFactory.select(member)
                .from(member)
                .where(member.id
                    .notIn(JPAExpressions.select(member.id)
                    .from(vacation)
                    .where(vacation.vacStartDate.loe(now)
                        .and(vacation.vacEndDate.goe(now))
                        .and(vacation.vacType.eq("병가")
                            .or(vacation.vacType.eq("휴가"))))))
                .fetch();

        for (MemberEntity memberEntity : memberEntityList) {
            List<CommuteEntity> commuteList = memberEntity.getCommuteEntityList();
            if (!commuteList.isEmpty()
                && memberEntity
                .getCommuteEntityList()
                .get(memberEntity
                    .getCommuteEntityList().size() - 1)
                .getStatus()
                .equals("휴가")) {
                CommuteEntity commuteEntity = CommuteEntity.builder()
                    .id(memberEntity
                        .getCommuteEntityList()
                        .get(memberEntity
                            .getCommuteEntityList().size() - 1)
                        .getId())
                    .work(0)
                    .status("미출근")
                    .memberEntity(memberEntity)
                    .build();

                commuteRepository.save(commuteEntity);
            }
        }

    }

    @Override
    public void deleteOverTimeVacation() {
        LocalDateTime now = LocalDateTime.now();
        vacationRepository.deleteByVacEndDateBefore(now.toLocalDate());
    }

    @Override
    public Page<VacationDto> vacationList(Pageable pageable, String subject, String search) {


        Page<VacationEntity> vacationEntityPage;

        if (subject == null || search == null) {
            vacationEntityPage = vacationRepository.findAll(pageable);
        } else if (subject.equals("name")) {
            vacationEntityPage = vacationRepository.findByMemberEntityNameContains(pageable, search);
        } else if (subject.equals("positionName")) {
            vacationEntityPage = vacationRepository.findByMemberEntityPositionEntityPositionNameContains(pageable, search);
        } else if (subject.equals("vacType")) {
            vacationEntityPage = vacationRepository.findByVacTypeContains(pageable, search);
        } else {
            vacationEntityPage = vacationRepository.findAll(pageable);
        }

        Page<VacationDto> vacationDtoPage = vacationEntityPage.map(vacationEntity ->
            VacationDto.builder()
                .id(vacationEntity.getId())
                .memberEntity(vacationEntity.getMemberEntity())
                .vacType(vacationEntity.getVacType())
                .vacStartDate(vacationEntity.getVacStartDate())
                .vacEndDate(vacationEntity.getVacEndDate())
                .vacDays(vacationEntity.getVacDays())
                .build()
        );
        return vacationDtoPage;
    }

    @Override
    public List<VacationDto> myVacation(Long id) {

        List<VacationEntity> vacationEntityList = vacationRepository.findByMemberEntityId(id);

        List<VacationDto> vacationDtoList = vacationEntityList.stream().map(vacationEntity ->
            VacationDto.builder()
                .id(vacationEntity.getId())
                .memberEntity(vacationEntity.getMemberEntity())
                .vacType(vacationEntity.getVacType())
                .vacStartDate(vacationEntity.getVacStartDate())
                .vacEndDate(vacationEntity.getVacEndDate())
                .vacDays(vacationEntity.getVacDays())
                .build()
        ).collect(Collectors.toList());

        return vacationDtoList;
    }
}
