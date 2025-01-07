package webapp.resumeanalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webapp.resumeanalyzer.domain.model.Experience;

/**
 * Тест сериализации/десериализации JSON для сущности Experience.
 */
@DisplayName("Тест проверки сущности Experience")
public class ExperienceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("сравнение после сериализации и десериализации")
    void testExperienceSerializationAndDeserializationEquals() throws JsonProcessingException {
        Experience experience = createSampleExperience();
        String jsonString = objectMapper.writeValueAsString(experience);
        Experience deserializedExperience = objectMapper.readValue(jsonString, Experience.class);
        assertEquals(experience.getDescription(), deserializedExperience.getDescription());
        assertEquals(experience.getPosition(), deserializedExperience.getPosition());
        assertEquals(experience.getFrom_year(), deserializedExperience.getFrom_year());
        assertEquals(experience.getTo_year(), deserializedExperience.getTo_year());
        assertEquals(experience.getName(), deserializedExperience.getName());
    }

    @Test
    @DisplayName("сравнение с NULL")
    void testExperienceSerializationAndDeserializationNullValues() throws JsonProcessingException {
        Experience experience = new Experience();
        String jsonString = objectMapper.writeValueAsString(experience);
        Experience deserializedExperience = objectMapper.readValue(jsonString, Experience.class);
        assertNull(deserializedExperience.getId());
        assertNull(deserializedExperience.getDescription());
        assertNull(deserializedExperience.getPosition());
        assertNull(deserializedExperience.getFrom_year());
        assertNull(deserializedExperience.getTo_year());
        assertNull(deserializedExperience.getName());
    }

    private Experience createSampleExperience() {
        return new Experience(UUID.randomUUID(), "X", "Y", "1970",
                "2000", "Z");
    }
}
