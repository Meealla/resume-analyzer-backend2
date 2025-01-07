package webapp.resumeanalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webapp.resumeanalyzer.domain.model.Education;

/**
 * Тест сериализации/десериализации JSON для сущности Education.
 */
@DisplayName("Тест проверки сущности Education")
public class EducationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("сравнение после сериализации и десериализации")
    void testEducationSerializationAndDeserializationEquals() throws JsonProcessingException {
        Education education = createSampleEducation();
        String jsonString = objectMapper.writeValueAsString(education);
        Education deserializedEducation = objectMapper.readValue(jsonString, Education.class);
        assertEquals(education.getDescription(), deserializedEducation.getDescription());
        assertEquals(education.getPosition(), deserializedEducation.getPosition());
        assertEquals(education.getFrom_year(), deserializedEducation.getFrom_year());
        assertEquals(education.getTo_year(), deserializedEducation.getTo_year());
        assertEquals(education.getName(), deserializedEducation.getName());
    }

    @Test
    @DisplayName("сравнение с NULL")
    void testEducationSerializationAndDeserializationNullValues() throws JsonProcessingException {
        Education education = new Education();
        String jsonString = objectMapper.writeValueAsString(education);
        Education deserializedEducation = objectMapper.readValue(jsonString, Education.class);
        assertNull(deserializedEducation.getId());
        assertNull(deserializedEducation.getDescription());
        assertNull(deserializedEducation.getPosition());
        assertNull(deserializedEducation.getFrom_year());
        assertNull(deserializedEducation.getTo_year());
        assertNull(deserializedEducation.getName());
    }

    private Education createSampleEducation() {
        return new Education(UUID.randomUUID(), "X", "Y", "1970",
                "2000", "Z");
    }
}
