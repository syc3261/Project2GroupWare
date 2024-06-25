package org.spring.groupAir.board.controller;


import lombok.RequiredArgsConstructor;
import org.spring.groupAir.board.dto.BoardDto;
import org.spring.groupAir.board.dto.BoardReplyDto;
import org.spring.groupAir.board.service.ReplyService;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reply")
public class ReplyController {

  private final ReplyService replyService;


  @PostMapping("/replyWrite")
  public String replyWrite(BoardReplyDto boardReplyDto ,Model model ,
                           @AuthenticationPrincipal MyUserDetailsImpl myUserDetails){

    model.addAttribute("memberName", myUserDetails.getMemberEntity());
    replyService.insertReply(boardReplyDto);

    return "redirect:board/detail"+ boardReplyDto.getBoardId();

  }

/*  @GetMapping("replyList{id}")
  @ResponseBody
  public List<BoardReplyDto> replyDtoList(@PathVariable("id")Long id) {

    List<BoardReplyDto> replyDtoList=replyService.ajaxReplyList(id);

    return replyDtoList;
  }*/


  @PostMapping("/ajaxWrite")
  @ResponseBody
  public BoardReplyDto ajaxWrite(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails ,
                                 Model model, BoardReplyDto boardReplyDto, BoardDto boardDto) {

    model.addAttribute("memberName" , myUserDetails.getMemberEntity().getName());

    BoardReplyDto reply = replyService.ajaxInsert(boardReplyDto);

    return reply;
  }

  @GetMapping("/replyList{id}")
  @ResponseBody
  public List<BoardReplyDto> replyList(@PathVariable("id")Long id ) {


    List<BoardReplyDto> replyList = replyService.ajaxReplyList(id);


    return replyList;

  }

  @GetMapping("/boardReplyDelete/{id}")
  public String boardReplyDelete(@PathVariable("id") Long id) {

    Long boardId = replyService.boardReplyDeleteById(id);

    return "redirect:/board/detail/" + boardId;
  }


}
