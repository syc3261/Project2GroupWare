package org.spring.groupAir.board.service.serviceInterface;

import org.spring.groupAir.board.dto.BoardReplyDto;

import java.util.List;

public interface BoardReplyServiceInterface {



  void insertReply(BoardReplyDto boardReplyDto);

/*  List<BoardReplyDto> ajaxReplyList(Long id);*/

  BoardReplyDto ajaxInsert(BoardReplyDto boardReplyDto);

  List<BoardReplyDto> replyList(Long id);

  List<BoardReplyDto> ajaxReplyList(Long id);

  Long boardReplyDeleteById(Long id);
}
