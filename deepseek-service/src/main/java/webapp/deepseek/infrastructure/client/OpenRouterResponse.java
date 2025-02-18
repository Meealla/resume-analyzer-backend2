package webapp.deepseek.infrastructure.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Ответ от API OpenRouter
 * Содержит варианты ответов и данные об использовании токенов
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouterResponse {
    private List<Choice> choices;
    private UsageTokens usageTokens;

    // Наилучший вариант ответа
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private Message message;
    }

    // Tекст ответа
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String content;
    }

    // Расход токенов. Сколько токенов потрачено на запрос+ответ
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UsageTokens {
        private int total_tokens;
    }
}