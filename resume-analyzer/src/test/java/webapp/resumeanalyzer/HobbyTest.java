package webapp.resumeanalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webapp.resumeanalyzer.domain.model.Hobby;

/**
 * Тест сериализации/десериализации JSON для сущности Hobby.
 */
@DisplayName("Тест проверки сущности Hobby")
public class HobbyTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("сравнение после сериализации и десериализации")
    void testHobbySerializationAndDeserializationEquals() throws JsonProcessingException {
        Hobby hobby = createSampleHobby();
        String jsonString = objectMapper.writeValueAsString(hobby);
        Hobby deserializedHobby = objectMapper.readValue(jsonString, Hobby.class);
        assertEquals(hobby.getHobby(), deserializedHobby.getHobby());
    }

    @Test
    @DisplayName("сравнение с NULL")
    void testHobbySerializationAndDeserializationNullValues() throws JsonProcessingException {
        Hobby hobby = new Hobby();
        String jsonString = objectMapper.writeValueAsString(hobby);
        Hobby deserializedHobby = objectMapper.readValue(jsonString, Hobby.class);
        assertNull(deserializedHobby.getId());
        assertNull(deserializedHobby.getHobby());
    }

    private Hobby createSampleHobby() {
        return new Hobby(UUID.randomUUID(), "X");
    }
}
