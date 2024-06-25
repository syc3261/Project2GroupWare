package org.spring.groupAir.board.dto;

import lombok.*;
import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.board.entity.BoardReplyEntity;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardReplyDto {


  private Long id;


  private String replyWriter;

  private String replyContent;


  private BoardEntity boardEntity;

  private Long boardId;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;




  public static BoardReplyDto toSelectReplyDto(BoardReplyEntity replyEntity) {
    BoardReplyDto boardReplyDto = new BoardReplyDto();
    boardReplyDto.setId(replyEntity.getId());
    boardReplyDto.setReplyWriter(replyEntity.getReplyWriter());
    boardReplyDto.setReplyContent(replyEntity.getReplyContent());

    boardReplyDto.setBoardEntity(replyEntity.getBoardEntity());
    boardReplyDto.setCreateTime(replyEntity.getCreateTime());
    boardReplyDto.setUpdateTime(replyEntity.getUpdateTime());
    return  boardReplyDto;

  }

  public static BoardReplyDto toAjaxReplyEntity(BoardReplyEntity replyEntity) {

    BoardReplyDto replyDto = new BoardReplyDto();
    replyDto.setId(replyEntity.getId());
    replyDto.setReplyWriter(replyEntity.getReplyWriter());
    replyDto.setReplyContent(replyEntity.getReplyContent());

//    replyDto.setBoardId(replyEntity.getBoardEntity().getId());
    replyDto.setBoardEntity(replyEntity.getBoardEntity());
    replyDto.setCreateTime(replyEntity.getCreateTime());
    replyDto.setUpdateTime(replyEntity.getUpdateTime());

    return replyDto;
  }
}
