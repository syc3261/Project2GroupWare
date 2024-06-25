package org.spring.groupAir.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.groupAir.board.dto.BoardFileDto;
import org.spring.groupAir.contraint.BaseTimeEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "boardFile")
public class BoardFileEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardFile_id")
    private Long id;

    @Column(nullable = false)
    private String boardNewFile;

    @Column(nullable = false)
    private String boardOldFile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;




  public static BoardFileEntity toInsertFile(BoardFileDto fileDto) {
      BoardFileEntity fileEntity = new BoardFileEntity();
      fileEntity.setBoardNewFile(fileDto.getBoardNewFile());
      fileEntity.setBoardOldFile(fileDto.getBoardOldFile());
      fileEntity.setBoardEntity(fileDto.getBoardEntity());
      return fileEntity;
  }

}
