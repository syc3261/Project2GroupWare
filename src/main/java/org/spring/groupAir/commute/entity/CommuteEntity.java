package org.spring.groupAir.commute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.groupAir.contraint.BaseTimeEntity;
import org.spring.groupAir.member.entity.MemberEntity;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "commute")
public class CommuteEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commute_id")
    private Long id;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int work;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    private Duration totalWork;

    @Column(nullable = true)
    private LocalDateTime inTime;

    @Column(nullable = true)
    private LocalDateTime outTime;

    @Column(nullable = true)
    private String cause;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private MemberEntity memberEntity;


}
