package org.spring.groupAir.chatbot.repository;

import org.spring.groupAir.chatbot.entity.ChatBotIntentionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatBotIntentionRepository extends JpaRepository<ChatBotIntentionEntity, Long> {
    Optional<ChatBotIntentionEntity> findByNameAndUpper(String token, ChatBotIntentionEntity upper);
}
