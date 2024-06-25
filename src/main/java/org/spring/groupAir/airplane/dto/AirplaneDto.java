package org.spring.groupAir.airplane.dto;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.groupAir.member.entity.MemberEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AirplaneDto {

    private Long id;

    @NotNull(message = "도착시간을 입력하세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fromTime;
    
    @NotBlank(message = "도착지를 입력하세요")
    private String fromArea;

    @NotNull(message = "출발시간을 입력하세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime toTime;

    @NotBlank(message = "출발지를 입력하세요")
    private String toArea;

    private int timeTaken;

    @NotBlank(message = "비행기를 입력하세요")
    private String airplane;

    private String status;

    private MemberEntity memberEntity;

    private Long memberId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
