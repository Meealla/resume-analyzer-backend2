package webapp.deepseek.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Часть запроса (OpenRouterRequest), хранит роль отправителя (пользователь/ассистент) и текст сообщения
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String role;
    private String content;
}