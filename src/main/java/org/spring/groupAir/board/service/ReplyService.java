package org.spring.groupAir.board.service;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.board.dto.BoardReplyDto;
import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.board.entity.BoardReplyEntity;
import org.spring.groupAir.board.repository.BoardReplyRepository;
import org.spring.groupAir.board.repository.BoardRepository;
import org.spring.groupAir.board.service.serviceInterface.BoardReplyServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ReplyService implements BoardReplyServiceInterface {

  private final BoardRepository boardRepository;
  private final BoardReplyRepository replyRepository;
  @Override
  public List<BoardReplyDto> replyList(Long id) {

    BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(()->{
      throw new IllegalArgumentException("아이디Fail");

    });

    // BoardEntity - > board_id

    List<BoardReplyEntity> replyEntities = replyRepository.findAllByBoardEntity(boardEntity);


    List<BoardReplyDto> replyDtos = new ArrayList<>();


    for (BoardReplyEntity replyEntity : replyEntities) {
      BoardReplyDto replyDto = BoardReplyDto.toSelectReplyDto(replyEntity);
      replyDtos.add(replyDto);
    }



    return replyDtos;
  }

  @Override
  public List<BoardReplyDto> ajaxReplyList(Long id) {

    Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
    if (optionalBoardEntity.isPresent()) {
      BoardEntity boardEntity=optionalBoardEntity.get();

      List<BoardReplyEntity> replyEntities = replyRepository.replyJoinBoard(boardEntity.getId());
      List<BoardReplyDto> boardReplyDtos= new ArrayList<>();

      for (BoardReplyEntity replyEntity:replyEntities) {
        BoardReplyDto replyDto = BoardReplyDto.toSelectReplyDto(replyEntity);

        boardReplyDtos.add(replyDto);


      }


      return boardReplyDtos;
    }

    return null;
  }

  @Override
  public Long boardReplyDeleteById(Long id) {

    Long boardId = replyRepository.findById(id).get().getBoardEntity().getId(); // 댓글 if 찾기

    if(boardId != null) {
      replyRepository.deleteById(id);

    } else {
      System.out.println("댓글 작성 식패");
    }

    return boardId;
  }


  @Override
  public void insertReply(BoardReplyDto boardReplyDto) {
    // 1. boardId가 있는지 확인


    BoardEntity boardEntity = boardRepository.findById(boardReplyDto.getBoardId()).orElseThrow(() ->{
      throw new IllegalArgumentException("아이디가 없습니다!");
    });

    if (boardEntity != null ) {

      // 2. reply 저장

      BoardEntity boardEntity1 = BoardEntity.builder()
          .id(boardReplyDto.getBoardId())
          .build();

      boardReplyDto.setBoardEntity(boardEntity1);

      BoardReplyEntity replyEntity = BoardReplyEntity.toInsertReplyEntity(boardReplyDto);

      replyRepository.save(replyEntity);

    }

  }


  ////////////////
/*  @Override
  public List<BoardReplyDto> ajaxReplyList(Long id) {

    Optional<BoardEntity> optionalBoardEntity=boardRepository.findById(id);
    if (optionalBoardEntity.isPresent()) {
      BoardEntity boardEntity = optionalBoardEntity.get();

      List<BoardReplyEntity> replyEntities = replyRepository.replyJoinBoard(boardEntity.getId());
      List<BoardReplyDto> boardReplyDtos = new ArrayList<>();

      for (BoardReplyEntity replyEntity:replyEntities) {

        BoardReplyDto replyDto = BoardReplyDto.toSelectReplyDto(replyEntity);
        boardReplyDtos.add(replyDto);
      }
    }
    return null;
  }*/

  @Override
  public BoardReplyDto ajaxInsert(BoardReplyDto boardReplyDto) {
    // 1. 게시글 확인

    BoardEntity boardEntity = boardRepository.findById(boardReplyDto.getBoardId()).orElseThrow(()->{
      throw new IllegalArgumentException("아이디가 없습니다.");
    });

    // 2. 댓글 작성

    boardReplyDto.setBoardEntity(BoardEntity.builder().id(boardReplyDto.getBoardId()).build());

    BoardReplyEntity replyEntity = BoardReplyEntity.toInsertReplyEntity(boardReplyDto);
    Long id = replyRepository.save(replyEntity).getId();
    BoardReplyEntity replyEntity1 = replyRepository.findById(id).orElseThrow(()->{
      throw new IllegalArgumentException("아이디 Fail");

    });

    return BoardReplyDto.toAjaxReplyEntity(replyEntity1);
  }
}
