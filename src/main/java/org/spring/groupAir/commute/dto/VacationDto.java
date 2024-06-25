package org.spring.groupAir.commute.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.groupAir.commute.entity.CommuteEntity;
import org.spring.groupAir.member.entity.MemberEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VacationDto {

    private Long id;

    private String vacType;

    private int vacDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacEndDate;

    private MemberEntity memberEntity;

    private Long employeeId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
