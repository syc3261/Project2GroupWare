package org.spring.groupAir.member.repository;

import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.MemberFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberFileRepository extends JpaRepository<MemberFileEntity, Long> {


    Optional<MemberFileEntity> findByMemberEntityId(Long id);
}
