package org.spring.groupAir.sign.repository;

import org.spring.groupAir.sign.entity.SignFileEntity;
import org.spring.groupAir.sign.entity.SignStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignStatusRepository  extends JpaRepository<SignStatusEntity, Long> {
}
