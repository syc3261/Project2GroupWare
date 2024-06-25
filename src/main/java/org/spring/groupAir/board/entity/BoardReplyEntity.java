package org.spring.groupAir.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.groupAir.board.dto.BoardReplyDto;
import org.spring.groupAir.contraint.BaseTimeEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "boardReply")
public class BoardReplyEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardReply_id")
    private Long id;

    @Column(nullable = false)
    private String replyWriter;

    @Column(nullable = false)
    private String replyContent;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

  public static BoardReplyEntity toInsertReplyEntity(BoardReplyDto boardReplyDto) {
      BoardReplyEntity replyEntity = new BoardReplyEntity();
      replyEntity.setId(boardReplyDto.getId());
      replyEntity.setBoardEntity(boardReplyDto.getBoardEntity());
      replyEntity.setReplyContent(boardReplyDto.getReplyContent());
      replyEntity.setReplyWriter(boardReplyDto.getReplyWriter());

      return replyEntity;
  }
}
