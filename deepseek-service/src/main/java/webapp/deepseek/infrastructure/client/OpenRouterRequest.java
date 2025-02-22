package webapp.deepseek.infrastructure.client;

import lombok.Data;
import webapp.deepseek.domain.ChatMessage;

import java.util.List;

/**
 * Тело запроса к OpenRouter API
 * Содержит имя модели и историю диалога с ии
 */
@Data
public class OpenRouterRequest {
    private String model;
    private List<ChatMessage> messages;
}