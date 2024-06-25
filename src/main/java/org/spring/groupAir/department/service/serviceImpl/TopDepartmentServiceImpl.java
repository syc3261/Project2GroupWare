package org.spring.groupAir.department.service.serviceImpl;

import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.member.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopDepartmentServiceImpl {
    Page<TopDepartmentDto> pageList(Pageable pageable);

    List<TopDepartmentDto> List(TopDepartmentDto topDepartmentDto);

    void write(TopDepartmentDto topDepartmentDto);

    TopDepartmentDto detail(Long id);

    void detele(Long id);

    void update(TopDepartmentDto topDepartmentDto);

    List<TopDepartmentDto> ListManager(TopDepartmentDto topDepartmentDto, Long id);
}
