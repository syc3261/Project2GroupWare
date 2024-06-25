package org.spring.groupAir.commute.dto;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.groupAir.commute.entity.CommuteEntity;
import org.spring.groupAir.member.entity.MemberEntity;

import javax.persistence.Column;
import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommuteDto {

    private Long id;

    private String status;

    private LocalDateTime inTime;

    private LocalDateTime outTime;

    private String cause;

    private int work;

    private Duration totalWork;

    private MemberEntity memberEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static CommuteDto toCommuteDto(CommuteEntity commuteEntity) {

        CommuteDto commuteDto = new CommuteDto();

        commuteDto.setId(commuteEntity.getId());
        commuteDto.setInTime(commuteEntity.getInTime());
        commuteDto.setOutTime(commuteEntity.getOutTime());
        commuteDto.setCause(commuteEntity.getCause());
        commuteDto.setWork(commuteEntity.getWork());
        commuteDto.setTotalWork(commuteEntity.getTotalWork());
        commuteDto.setStatus(commuteEntity.getStatus());
        commuteDto.setMemberEntity(commuteEntity.getMemberEntity());
        commuteDto.setCreateTime(commuteEntity.getCreateTime());
        commuteDto.setUpdateTime(commuteEntity.getUpdateTime());

        return commuteDto;
    }

}
