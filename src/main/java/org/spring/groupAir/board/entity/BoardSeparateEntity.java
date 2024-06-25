package org.spring.groupAir.board.entity;

import lombok.*;
import org.spring.groupAir.contraint.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "boardSeparate")
public class BoardSeparateEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardSeparate_id")  // id
    private Long id;


    @Column(nullable = false)
    private String boardSeparateName;

    @OneToMany(mappedBy = "boardSeparateEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<BoardEntity> boardEntityList;
}
