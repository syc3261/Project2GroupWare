package org.spring.groupAir.board.dto;

import lombok.*;
import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.board.entity.BoardFileEntity;
import org.spring.groupAir.board.entity.BoardReplyEntity;
import org.spring.groupAir.board.entity.BoardSeparateEntity;
import org.spring.groupAir.member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
  private Long id;

  @NotBlank(message = "title 입력해주세요")
  private String title;

  @NotBlank(message = "content 를 입력해주세요")
  private String content;


  private int hit;

  private Long memberId;  // 게시판 글 작 성 시

  private Long boardSeparateId;

  private String boardSeparateName;

  private String writer;

  private int boardAttachFile; //게시글 작성시 파일이 존재하면 1, 없으면 0

  private MultipartFile boardFile;

  private MemberEntity memberEntity; // member_id 12345

  private BoardSeparateEntity boardSeparateEntity;

  private List<BoardReplyEntity> boardReplyEntityList;

  private List<BoardFileEntity> boardFileEntityList;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  // 파일
  private String boardNewFile;

  private  String boardOldFile;

  private  String boardFileName;




  public static BoardDto toSelectBoardDto(BoardEntity boardEntity) {

    BoardDto boardDto = new BoardDto();
    boardDto.setId(boardEntity.getId());
    boardDto.setTitle(boardEntity.getTitle());
    boardDto.setWriter(boardEntity.getWriter());
    boardDto.setContent(boardEntity.getContent());
    boardDto.setHit(boardEntity.getHit());
    boardDto.setMemberEntity(boardEntity.getMemberEntity());
    boardDto.setBoardAttachFile(boardEntity.getBoardAttachFile());
    boardDto.setCreateTime(boardEntity.getCreateTime());
    boardDto.setUpdateTime(boardEntity.getUpdateTime());
    boardDto.setMemberId(boardEntity.getMemberEntity().getId());

    // 얘가 왜 안돼 ?
   boardDto.setBoardSeparateId(boardEntity.getBoardSeparateEntity().getId());
   boardDto.setBoardSeparateEntity(boardEntity.getBoardSeparateEntity());


    if (boardEntity.getBoardAttachFile()==0) {
      // 파일이 x
      boardDto.setBoardAttachFile(boardEntity.getBoardAttachFile()); // 0
    } else  {
       // 파일이  0 -> 파일 정보
      boardDto.setBoardAttachFile(boardEntity.getBoardAttachFile());

      boardDto.setBoardNewFile(boardEntity.getBoardFileEntityList().get(0).getBoardNewFile());

      boardDto.setBoardOldFile(boardEntity.getBoardFileEntityList().get(0).getBoardOldFile());


    }

    return boardDto;

  }


  public static BoardDto toBoardDto(BoardEntity boardEntity) {
    BoardDto boardDto = new BoardDto();
    boardDto.setId(boardEntity.getId());
    boardDto.setTitle(boardEntity.getTitle());
    boardDto.setWriter(boardEntity.getWriter());
    boardDto.setContent(boardEntity.getContent());
    boardDto.setHit(boardEntity.getHit());
    boardDto.setBoardAttachFile(boardEntity.getBoardAttachFile());
    boardDto.setMemberEntity(boardEntity.getMemberEntity());
    boardDto.setBoardSeparateEntity(boardEntity.getBoardSeparateEntity());
    boardDto.setBoardReplyEntityList(boardEntity.getBoardReplyEntityList());
    boardDto.setBoardFileEntityList(boardEntity.getBoardFileEntityList());
    boardDto.setMemberId(boardEntity.getMemberEntity().getId());
    boardDto.setBoardSeparateId(boardEntity.getBoardSeparateEntity().getId());
    boardDto.setUpdateTime(boardEntity.getUpdateTime());
    boardDto.setCreateTime(boardEntity.getCreateTime());
    return boardDto;
  }
}
