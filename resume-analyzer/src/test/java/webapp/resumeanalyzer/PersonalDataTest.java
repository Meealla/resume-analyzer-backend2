package webapp.resumeanalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webapp.resumeanalyzer.domain.model.PersonalData;

/**
 * Тест сериализации/десериализации JSON для сущности PersonalData.
 */
@DisplayName("Тест проверки сущности PersonalData")
public class PersonalDataTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("сравнение после сериализации и десериализации")
    void testPersonalDataSerializationAndDeserializationEquals() throws JsonProcessingException {
        PersonalData personalData = createSamplePersonalData();
        String jsonString = objectMapper.writeValueAsString(personalData);
        PersonalData deserializedPersonalData = objectMapper.readValue(jsonString,
                PersonalData.class);
        assertEquals(personalData.getFull_name(), deserializedPersonalData.getFull_name());
        assertEquals(personalData.getAddress(), deserializedPersonalData.getAddress());
        assertEquals(personalData.getBio(), deserializedPersonalData.getBio());
        assertEquals(personalData.getPosition(), deserializedPersonalData.getPosition());
        assertEquals(personalData.getPhone(), deserializedPersonalData.getPhone());
        assertEquals(personalData.getWebsite(), deserializedPersonalData.getWebsite());
        assertEquals(personalData.getEmail(), deserializedPersonalData.getEmail());
    }

    @Test
    @DisplayName("сравнение с NULL")
    void testPersonalDataSerializationAndDeserializationNullValues()
            throws JsonProcessingException {
        PersonalData personalData = new PersonalData();
        String jsonString = objectMapper.writeValueAsString(personalData);
        PersonalData deserializedPersonalData = objectMapper.readValue(jsonString,
                PersonalData.class);
        assertNull(deserializedPersonalData.getId());
        assertNull(deserializedPersonalData.getFull_name());
        assertNull(deserializedPersonalData.getAddress());
        assertNull(deserializedPersonalData.getBio());
        assertNull(deserializedPersonalData.getPosition());
        assertNull(deserializedPersonalData.getPhone());
        assertNull(deserializedPersonalData.getWebsite());
        assertNull(deserializedPersonalData.getEmail());
    }

    private PersonalData createSamplePersonalData() {
        return new PersonalData(UUID.randomUUID(), "ABC", "A", "B",
                "C", 100L, "X", "Y@Z.ru");
    }
}
