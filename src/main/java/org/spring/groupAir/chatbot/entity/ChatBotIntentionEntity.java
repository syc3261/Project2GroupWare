package org.spring.groupAir.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Intention")
@Entity
public class ChatBotIntentionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JoinColumn
    @ManyToOne
    private AnswerEntity answerEntity;

    @JoinColumn
    @ManyToOne
    private ChatBotIntentionEntity upper;

}
