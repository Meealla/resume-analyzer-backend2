package webapp.resumeanalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webapp.resumeanalyzer.domain.model.SocialLink;

/**
 * Тест сериализации/десериализации JSON для сущности SocialLink.
 */
@DisplayName("Тест проверки сущности SocialLink")
public class SocialLinkTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("сравнение после сериализации и десериализации")
    void testSocialLinkSerializationAndDeserializationEquals() throws JsonProcessingException {
        SocialLink socialLink = createSampleSocialLink();
        String jsonString = objectMapper.writeValueAsString(socialLink);
        SocialLink deserializedSocialLink = objectMapper.readValue(jsonString, SocialLink.class);
        assertEquals(socialLink.getLink(), deserializedSocialLink.getLink());
        assertEquals(socialLink.getName(), deserializedSocialLink.getName());
    }

    @Test
    @DisplayName("сравнение с NULL")
    void testSocialLinkSerializationAndDeserializationNullValues() throws JsonProcessingException {
        SocialLink socialLink = new SocialLink();
        String jsonString = objectMapper.writeValueAsString(socialLink);
        SocialLink deserializedSocialLink = objectMapper.readValue(jsonString, SocialLink.class);
        assertNull(deserializedSocialLink.getId());
        assertNull(deserializedSocialLink.getLink());
        assertNull(deserializedSocialLink.getName());
    }

    private SocialLink createSampleSocialLink() {
        return new SocialLink(UUID.randomUUID(), "X", "Y");
    }
}
