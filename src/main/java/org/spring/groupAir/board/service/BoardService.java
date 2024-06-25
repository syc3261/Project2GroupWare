package org.spring.groupAir.board.service;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.board.dto.BoardDto;
import org.spring.groupAir.board.dto.BoardFileDto;
import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.board.entity.BoardFileEntity;
import org.spring.groupAir.board.entity.BoardSeparateEntity;
import org.spring.groupAir.board.repository.BoardFileRepository;
import org.spring.groupAir.board.repository.BoardRepository;
import org.spring.groupAir.board.repository.BoardSeparateRepository;
import org.spring.groupAir.board.service.serviceInterface.BoardServiceInterface;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.dto.MemberFileDto;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.MemberFileEntity;
import org.spring.groupAir.member.entity.PositionEntity;
import org.spring.groupAir.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // 생성자를 자동으로 생성
@Transactional
public class BoardService implements BoardServiceInterface {

  private  final BoardRepository boardRepository;
  private final BoardFileRepository fileRepository;
  private final BoardSeparateRepository boardSeparateRepository;
  private final BoardFileRepository boardFileRepository;
  private final MemberRepository memberRepository;

  @Override
  public void insertBoard(BoardDto boardDto) throws IOException {
      // 파일이 없는 경우
      //dto->entity
    if(boardDto.getBoardFile().isEmpty()) {

      boardDto.setMemberEntity(MemberEntity.builder()
          .id(boardDto.getMemberId())
          .build());

      System.out.println(">>>>>" + boardDto.getBoardSeparateId());

      BoardSeparateEntity boardSeparateEntity = boardSeparateRepository.findById(boardDto.getBoardSeparateId()).orElseThrow(IllegalArgumentException::new);

      boardDto.setBoardSeparateEntity(BoardSeparateEntity.builder().id(boardDto.getBoardSeparateId()).build());

      BoardEntity boardEntity = BoardEntity.toInsertBoardEntity(boardDto);

      boardRepository.save(boardEntity);

    } else {
      BoardSeparateEntity boardSeparateEntity = boardSeparateRepository.findById(boardDto.getBoardSeparateId()).orElseThrow(IllegalArgumentException::new);

      boardDto.setBoardSeparateEntity(BoardSeparateEntity.builder().id(boardDto.getBoardSeparateId()).build());

      // 파일이 있는 경우
      // 로컬에 실제 파일을 저장 시킴
      MultipartFile boardFile = boardDto.getBoardFile(); // 실제 파일

      String oldFileName = boardFile.getOriginalFilename(); //   원본 이미지 파일 이름
      UUID uuid = UUID.randomUUID();// 랜덤하게
      String newFileName = uuid + "_" + oldFileName;; // 램덤하게 이름 들어감
      String fileSavePath = "C:/groupAir/" + newFileName; // 여기 경로로 저장됨
      boardFile.transferTo(new File(fileSavePath)); // IoException

      boardDto.setMemberEntity(MemberEntity.builder()
          .id(boardDto.getMemberId())
          .build());



      BoardEntity boardEntity = BoardEntity.toInsertFileBoardEntity(boardDto);
      Long id = boardRepository.save(boardEntity).getId();

      Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
      if(optionalBoardEntity.isPresent()) {
        //  게시글 0
        BoardEntity boardEntity1 = optionalBoardEntity.get(); // Entity
        // 게시글에 정상 저장되면 -> 파일 Entity 저장



        BoardFileDto fileDto = BoardFileDto.builder()
            .boardOldFile(oldFileName)
            .boardNewFile(newFileName)
            .boardEntity(boardEntity1)
            .build();



        BoardFileEntity fileEntity = BoardFileEntity.toInsertFile(fileDto);
        fileRepository.save(fileEntity);
      }
      else  {

        throw  new  IllegalArgumentException("아이디가 없습니다.");
      }
    }
  }


  @Override
  public Page<BoardDto> boardSearchPagingList(Pageable pageable, String subject, String search) {

    Page<BoardEntity> boardEntities = null;

    if (search == null || subject == null) {
      boardEntities = boardRepository.findAll(pageable);

    } else  {

      if (subject.equals("title")) {
        boardEntities  = boardRepository.findByTitleContaining(pageable, search);

      } else if (subject.equals("content")) {
        boardEntities = boardRepository.findByContentContaining(pageable, search);
      } else if (subject.equals("writer")) {
        boardEntities = boardRepository.findByWriterContaining(pageable,search);
      } else  {
        boardEntities = boardRepository.findAll(pageable);
      }
    }
    Page<BoardDto> boardDtos = boardEntities.map(BoardDto::toSelectBoardDto);

    return boardDtos;
  }


  @Override
  public BoardDto detail(Long id) {
    updateHit(id);
    Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

    if (optionalBoardEntity.isPresent()) {

      BoardEntity boardEntity = optionalBoardEntity.get(); //
      BoardDto boardDto = BoardDto.toSelectBoardDto(boardEntity);

    return boardDto;
    }
    throw new IllegalArgumentException("아이디가 Fail!");
  }
  private void updateHit(Long id) {
    boardRepository.updateHitById(id);
  }


  @Override
  public void deleteBoard(Long id) {

    BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(()->{

      throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.");
    });

    Optional<BoardFileEntity> optionalBoardFileEntity = fileRepository.findByBoardEntityId(boardEntity.getId());

    if (optionalBoardFileEntity.isPresent()) {
      String fileNewName = optionalBoardFileEntity.get().getBoardNewFile();
      String filePath = "C:/groupAir/" + fileNewName;
      File deleteFile = new File(filePath);

      if (deleteFile.exists()) {
        deleteFile.delete();
        System.out.println("파일을 삭제하였습니다.");
      } else {
        System.out.println("파일이 존재하지 않습니다.");
      }
    fileRepository.delete(optionalBoardFileEntity.get()); // 파일 테이블 레코드 삭제ㅐ
    }

  boardRepository.delete(boardEntity);
  }

  @Override
  public List<BoardEntity> getBoardsBySeparateId(Long boardSeparateId) {



    return boardRepository.findByBoardSeparateEntityId(boardSeparateId);
  }



  @Override
  public void boardUpdate(BoardDto boardDto) throws IOException {

    boardDto.setMemberEntity(MemberEntity.builder().id(boardDto.getMemberId()).build());

    if (boardDto == null || boardDto.getId() == null) {
      throw new IllegalArgumentException("boardDto 또는 ID가 null입니다.");
    }

    BoardEntity boardEntity = boardRepository.findById(boardDto.getId())
        .orElseThrow(() -> new RuntimeException("해당 아이디가 없습니다"));

    Optional<BoardFileEntity> optionalBoardFileEntity = boardFileRepository
        .findByBoardEntityId(boardDto.getId());

    optionalBoardFileEntity.ifPresent(boardFileEntity -> {
      String newFileName = boardFileEntity.getBoardNewFile();
      String filePath = "c:/groupAir/" + newFileName;
      File deleteFile = new File(filePath);
      if (deleteFile.exists()) {
        if (!deleteFile.delete()) {
          System.out.println("파일 삭제에 실패했습니다.");
        }
      } else {
        System.out.println("파일이 존재하지 않습니다.");
      }
      boardFileRepository.delete(boardFileEntity);
    });


    boolean isFilePresent = boardDto.getBoardFile() != null && !boardDto.getBoardFile().isEmpty();

    if (isFilePresent) {
      processFile(boardDto); // 이 부분에서 NullPointerException이 발생할 수 있습니다.
    }


    if (isFilePresent ) {
      boardEntity = BoardEntity.toUpdateFileBoardEntity1(boardDto);
    } else {
      boardEntity = BoardEntity.toUpdateFileBoardEntity0(boardDto);
    }

    boardRepository.save(boardEntity);
  }

  @Override
  public int board1() {

    int board1 = boardRepository.findByBoardSeparateEntityId(1L).size();

    return board1;
  }

  @Override
  public int board2() {

    int board2 = boardRepository.findByBoardSeparateEntityId(2L).size();

    return board2;
  }

  @Override
  public int board3() {

    int board3 = boardRepository.findByBoardSeparateEntityId(3L).size();

    return board3;
  }

  @Override
  public int board4() {

    int board4 = boardRepository.findByBoardSeparateEntityId(4L).size();

    return board4;
  }

  @Override
  public int myBoardCount(Long id) {

    int boardCount = boardRepository.findByMemberEntityId(id).size();

    return boardCount;
  }

  @Override
  public Page<BoardDto> getBoardsBySeparateIdPaged(Long boardSeparateId, Pageable pageable, String subject, String search) {

    Page<BoardEntity> boardEntities = null;


    if (search == null || subject == null) {
      boardEntities = boardRepository.findByBoardSeparateId(boardSeparateId, pageable);
    } else {
      if (subject.equals("title")) {
        boardEntities = boardRepository.findByBoardSeparateIdAndTitleContaining(boardSeparateId, search, pageable);
      } else if (subject.equals("content")) {
        boardEntities = boardRepository.findByBoardSeparateIdAndContentContaining(boardSeparateId, search, pageable);
      } else if (subject.equals("writer")) {
        boardEntities = boardRepository.findByBoardSeparateIdAndWriterContaining(boardSeparateId, search, pageable);
      } else {
        boardEntities = boardRepository.findByBoardSeparateId(boardSeparateId, pageable);
      }
    }

    Page<BoardDto> boardDtos = boardEntities.map(BoardDto::toSelectBoardDto);
    return boardDtos;

  }

  private void processFile(BoardDto boardDto) throws IOException {
    if (boardDto.getBoardFile() == null) {
      throw new IllegalArgumentException("MemberDto에 파일이 없습니다.");
    }

    MultipartFile boardFile = boardDto.getBoardFile();
    String oldFileName = boardFile.getOriginalFilename();
    UUID uuid = UUID.randomUUID();
    String newFileName = uuid + "_" + oldFileName;

    String savePath = "c:/groupAir/" + newFileName;
    boardFile.transferTo(new File(savePath));

    boardDto.setBoardFileName(newFileName);

    BoardFileDto boardFileDto = BoardFileDto.builder()
        .boardOldFile(oldFileName)
        .boardNewFile(newFileName)
        .boardEntity(boardRepository.findById(boardDto.getId()).orElseThrow(() ->
            new RuntimeException("해당 아이디가 존재하지 않습니다.")
        ))
        .build();

    BoardFileEntity boardFileEntity = BoardFileEntity.builder()
        .boardEntity(boardFileDto.getBoardEntity())
        .boardOldFile(boardFileDto.getBoardOldFile())
        .boardNewFile(boardFileDto.getBoardNewFile())
        .build();

    boardFileRepository.save(boardFileEntity);

  }


}
