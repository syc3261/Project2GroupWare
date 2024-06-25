package org.spring.groupAir.department.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.department.dto.DepartmentDto;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.department.entity.QDepartmentEntity;
import org.spring.groupAir.department.repository.DepartmentRepository;
import org.spring.groupAir.department.service.serviceImpl.DepartmentServiceImpl;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.entity.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService implements DepartmentServiceImpl {

    private final DepartmentRepository departmentRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    public void write(DepartmentDto departmentDto) {

        DepartmentEntity departmentEntity = DepartmentDto.toWriteDeEntity(departmentDto);

        departmentRepository.save(departmentEntity);
    }

    @Override
    public DepartmentDto detail(Long id) {

        DepartmentEntity departmentEntity = departmentRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("부서없음");
        });


        DepartmentDto departmentDto = DepartmentEntity.toUpdateDe(departmentEntity);

        return departmentDto;
    }

    @Override
    public void update(DepartmentDto departmentDto) {

        DepartmentEntity departmentEntity = departmentRepository.save(DepartmentEntity.builder()
                .id(departmentDto.getId())
                .departmentName(departmentDto.getDepartmentName())
                .topDepartmentEntity(departmentDto.getTopDepartmentEntity())
                .memberEntityList(departmentDto.getMemberEntityList())
                .build());

    }

    @Override
    public void delete(Long id) {

        departmentRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("삭제할 부서 X");
        });

        departmentRepository.deleteById(id);

    }


    @Override
    public List<DepartmentDto> getSubDepartments(Long topDepartmentId) {


        // 선택된 상위 부서에 해당하는 하위 부서 목록을 가져오는 로직을 구현
//        List<DepartmentDto> subDepartments = departmentRepository.findByTopDepartmentEntityId(topDepartmentId)
//                .stream()
//                .map(DepartmentDto::fromEntity)
//                .collect(Collectors.toList());


        // queryDsl
        QDepartmentEntity departmentEntity = QDepartmentEntity.departmentEntity;

        List<DepartmentDto> subDepartments = queryFactory.selectFrom(departmentEntity)
                .where(departmentEntity.topDepartmentEntity.id.eq(topDepartmentId))
                .fetch()
                .stream()
                .map(DepartmentDto::fromEntity)
                .collect(Collectors.toList());

        return subDepartments;
    }

    @Override
    public List<MemberDto> getMembers(Long deId) {

        DepartmentEntity departmentEntity = departmentRepository.findById(deId).get();

        List<MemberDto> member = departmentEntity.getMemberEntityList().stream().map(MemberDto::toMemberDto).collect(Collectors.toList());

        return member;
    }

    @Override
    public List<DepartmentDto> subDepartments() {

        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();

        List<DepartmentDto> departmentDtoList
                = departmentEntityList.stream().map(DepartmentDto::fromEntity).collect(Collectors.toList());

        return departmentDtoList;
    }
}
