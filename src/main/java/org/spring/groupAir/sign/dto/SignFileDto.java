package org.spring.groupAir.sign.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.groupAir.sign.entity.SignEntity;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignFileDto {
    private Long id;


    private String signNewFile;


    private String signOldFile;


    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private SignEntity signEntity;

}
