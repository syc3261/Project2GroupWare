package org.spring.groupAir.salary.service.serviceInterface;

import org.spring.groupAir.salary.dto.SalaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SalaryServiceInterface {
    void createSalary(Long id);

    Page<SalaryDto> memberSalary(Pageable pageable);

    SalaryDto updateSalary(Long id);

    void update(SalaryDto salaryDto);

    void overWork(Long id);

    void updateSalaryDate();

    List<SalaryDto> mySalary(Long id);
}
