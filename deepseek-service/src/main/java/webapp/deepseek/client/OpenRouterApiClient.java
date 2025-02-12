package webapp.deepseek.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Клиент для взаимодействия с API OpenRouter
 * Отправляет POST-запросы к эндпоинту /chat/completions
 */

@FeignClient(name = "openrouter-api", url = "https://openrouter.ai/api/v1")
public interface OpenRouterApiClient {

    @PostMapping(value = "/chat/completions", consumes = "application/json")
    OpenRouterResponse generateCompletion(
            @RequestHeader("Authorization") String apiKey,
            @RequestHeader("X-Title") String model,
            @RequestBody OpenRouterRequest request
    );

}
