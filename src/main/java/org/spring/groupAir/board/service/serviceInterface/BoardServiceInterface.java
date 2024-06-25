package org.spring.groupAir.board.service.serviceInterface;

import org.spring.groupAir.board.dto.BoardDto;
import org.spring.groupAir.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardServiceInterface {
  void insertBoard(BoardDto boardDto) throws IOException;


  Page<BoardDto> boardSearchPagingList(Pageable pageable, String subject, String search);

  BoardDto detail(Long id);


//  void update(BoardDto boardDto) throws IOException;


  void deleteBoard(Long id);


  List<BoardEntity> getBoardsBySeparateId(Long boardSeparateId);

//  BoardDto boardDetailUpdate(Long id);

//  void update(BoardDto boardDto) throws IOException;

  void boardUpdate(BoardDto boardDto) throws IOException;

    int board1();
    int board2();
    int board3();
    int board4();

    int myBoardCount(Long id);

  Page<BoardDto> getBoardsBySeparateIdPaged(Long boardSeparateId, Pageable pageable, String subject, String search);

  /*  BoardSeparateEntity getBoardSeparateById(Long boardSeparateId);*/
}
