package org.spring.groupAir.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageDto {

    private String today;

    private String time;

    private AnswerDto answerDto;

    public MessageDto today(String today) {
        this.today = today;
        return this;
    }

    public MessageDto answerDto(AnswerDto answerDto) {
        this.answerDto = answerDto;
        return this;
    }
}
