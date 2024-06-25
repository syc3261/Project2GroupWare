package org.spring.groupAir.sign.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.groupAir.contraint.BaseTimeEntity;

import org.spring.groupAir.sign.dto.SignFileDto;


import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "signFile")
public class SignFileEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signFile_id")
    private Long id;

    @Column(nullable = false)
    private String signNewFile;

    @Column(nullable = false)
    private String signOldFile;

    @Column(nullable = false)
    private String content;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sign_id")
    private SignEntity signEntity;



    public static SignFileEntity toInsertFile(SignFileDto signFileDto){
        SignFileEntity signFileEntity=new SignFileEntity();
        signFileEntity.setContent(signFileDto.getContent());
        signFileEntity.setSignNewFile(signFileDto.getSignNewFile());
        signFileEntity.setSignOldFile(signFileDto.getSignOldFile());
        signFileEntity.setSignEntity(signFileDto.getSignEntity());

        return signFileEntity;
    }



}
