package org.spring.groupAir.board.dto;

import lombok.*;
import org.spring.groupAir.board.entity.BoardEntity;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileDto {

  private Long id;


  public String boardNewFile;


  public String boardOldFile;


  private BoardEntity boardEntity;
}
