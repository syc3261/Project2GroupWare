package org.spring.groupAir.board.repository;

import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.board.entity.BoardReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardReplyRepository  extends JpaRepository<BoardReplyEntity,Long> {


  List<BoardReplyEntity> findAllByBoardEntity(BoardEntity boardEntity);


  @Query(value = "select r.* from reply_tb1 r inner join r. board_tb1 b on r.id=b.id " +
   "where b.id=:id" , nativeQuery = true)
  List<BoardReplyEntity> replyJoinBoard(Long id);


  /*  List<BoardReplyEntity> replyJoinBoard(Long id); //// */
}
