package org.spring.groupAir.department.service;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.department.dto.DepartmentDto;
import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.department.entity.TopDepartmentEntity;
import org.spring.groupAir.department.repository.TopDepartmentRepository;
import org.spring.groupAir.department.service.serviceImpl.TopDepartmentServiceImpl;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopDepartmentService implements TopDepartmentServiceImpl {

    private final TopDepartmentRepository topDepartmentRepository;


    @Override
    public TopDepartmentDto detail(Long id) {

        TopDepartmentEntity topDepartmentEntity = topDepartmentRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("부서없음");
        });

        TopDepartmentDto topDepartmentDto = TopDepartmentEntity.toUpdateTopDe(topDepartmentEntity);

        return topDepartmentDto;
    }

    @Override
    public Page<TopDepartmentDto> pageList(Pageable pageable) {

        Page<TopDepartmentEntity> topDepartmentEntities = topDepartmentRepository.findAll(pageable);

        Page<TopDepartmentDto> topDepartmentDtos = topDepartmentEntities.map(TopDepartmentDto::toDepartmentDto);

        return topDepartmentDtos;
    }

    private final MemberRepository memberRepository;

    @Override
    public List<TopDepartmentDto> List(TopDepartmentDto topDepartmentDto) {

        List<TopDepartmentEntity> topDepartmentEntities = topDepartmentRepository.findAll();

        List<TopDepartmentDto> topDepartmentDtos = topDepartmentEntities.stream()
            .map(TopDepartmentDto::toDepartmentDto).collect(Collectors.toList());

        return topDepartmentDtos;
    }

    @Override
    public List<TopDepartmentDto> ListManager(TopDepartmentDto topDepartmentDto, Long id) {

        List<TopDepartmentEntity> topDepartmentEntities = topDepartmentRepository.findByDepartmentEntityListMemberEntityListId(id);


        return topDepartmentEntities.stream().map(TopDepartmentDto::toDepartmentDto).collect(Collectors.toList());
    }

    @Override
    public void write(TopDepartmentDto topDepartmentDto) {

        TopDepartmentEntity topDepartmentEntity = TopDepartmentEntity.toTopDeEntity(topDepartmentDto);

        topDepartmentRepository.save(topDepartmentEntity);

    }

    @Override
    public void update(TopDepartmentDto topDepartmentDto) {

        TopDepartmentEntity topDepartmentEntity = topDepartmentRepository.save(TopDepartmentEntity.builder()
                .id(topDepartmentDto.getId())
                .topDepartmentName(topDepartmentDto.getTopDepartmentName())
                .departmentEntityList(topDepartmentDto.getDepartmentEntityList())
                .build());
    }

    @Override
    public void detele(Long id) {

        topDepartmentRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("상위부서 X");
        });

        topDepartmentRepository.deleteById(id);

    }
}
