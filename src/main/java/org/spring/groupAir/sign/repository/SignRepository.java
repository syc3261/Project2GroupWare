package org.spring.groupAir.sign.repository;

import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.sign.entity.SignEntity;
import org.spring.groupAir.sign.entity.SignFileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.FileChannel;
import java.util.List;

public interface SignRepository  extends JpaRepository<SignEntity, Long> {
    Page<SignEntity> findByTitleContaining(Pageable pageable, String search);

    Page<SignEntity> findByApproveContaining(Pageable pageable, String search);

    Page<SignEntity> findByContentContaining(Pageable pageable, String search);

    Page<SignEntity> findByLastApproverContaining(Pageable pageable, String search);

    Page<SignEntity> findByMemberEntityName(String name, Pageable pageable);

    Page<SignEntity> findByLastApprover(Pageable pageable, String name);

    Page<SignEntity> findByTitleContains(Pageable pageable, String search, String name);

    Page<SignEntity> findByContentContains(Pageable pageable, String search, String name);


//    List<SignEntity> findAllBySubcontent(String subContent);

    List<SignEntity> findAllBySubContent(String subContent);

    Page<SignEntity> findByApprove(Pageable pageable, String name);


    Page<SignEntity> findAllByMemberEntity(Pageable pageable, String search, MemberEntity memberEntity);

    List<SignEntity> findAllByLastApproverAndSubContent(String name, String subContent);


    List<SignEntity> findByMemberEntityIdAndSubContent(Long id, String subContent);
}





