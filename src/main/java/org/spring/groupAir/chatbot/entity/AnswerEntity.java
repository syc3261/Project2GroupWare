package org.spring.groupAir.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.groupAir.chatbot.dto.AnswerDto;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer")
@Entity
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String keyword;

    public AnswerEntity keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public AnswerDto toAnswerDto() {
        return AnswerDto.builder()
                .id(id)
                .content(content)
                .keyword(keyword)
                .build();
    }
}
