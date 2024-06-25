package org.spring.groupAir.board.dto;

import lombok.*;
import org.spring.groupAir.board.entity.BoardEntity;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardSeparateDto {


  private Long id;


  private String boardSeparateName;


  private List<BoardEntity> boardEntityList;
}
