package org.spring.groupAir.commute.service.serviceInterface;

import org.spring.groupAir.commute.dto.VacationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacationServiceInterface {

    void vacationCreate(VacationDto vacationDto);

    int vacationPeople();

    int sickVacationPeople();

    void findVacationPerson();

    void deleteOverTimeVacation();

    Page<VacationDto> vacationList(Pageable pageable, String subject, String search);

    List<VacationDto> myVacation(Long id);
}
