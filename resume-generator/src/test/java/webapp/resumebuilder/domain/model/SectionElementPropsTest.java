package webapp.resumebuilder.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса SectionElementProps")
public class SectionElementPropsTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // Читаемый JSON

    @Test
    @DisplayName("Сериализация и десериализация с заполненными данными")
    public void testSerializationAndDeserialization() throws JsonProcessingException {
        // Arrange: создаём тестовый объект с данными
        UUID customId = UUID.randomUUID();
        Map<String, String> wrapperStyle = Map.of("border", "1px solid black", "margin", "10px");
        Map<String, String> textStyle = Map.of("color", "blue", "font-size", "14px");
        Map<String, String> inputStyle = Map.of("padding", "5px", "border-radius", "3px");
        Map<String, String> style = Map.of("background-color", "red");

        SectionElementProps props = new SectionElementProps(
                "uniqueKey",
                "Sample text",
                wrapperStyle,
                textStyle,
                inputStyle,
                "https://example.com",
                style
        );
        props.setId(customId);

        // Act: Сериализуем и десериализуем
        String json = objectMapper.writeValueAsString(props);
        SectionElementProps deserializedProps = objectMapper.readValue(json, SectionElementProps.class);

        // Assert: Проверяем корректность после десериализации
        assertNotNull(deserializedProps);
        assertEquals(customId, deserializedProps.getId());
        assertEquals("uniqueKey", deserializedProps.getKey());
        assertEquals("Sample text", deserializedProps.getText());
        assertEquals("https://example.com", deserializedProps.getUrl());
        assertEquals(wrapperStyle, deserializedProps.getWrapperStyle());
        assertEquals(textStyle, deserializedProps.getTextStyle());
        assertEquals(inputStyle, deserializedProps.getInputStyle());
        assertEquals(style, deserializedProps.getStyle());
    }

    @Test
    @DisplayName("Сериализация объекта с null-полями")
    public void testSerializationWithNullValues() throws JsonProcessingException {
        // Arrange: создаём объект с null-полями
        SectionElementProps props = new SectionElementProps();
        props.setKey("keyWithNulls");
        props.setText(null);
        props.setWrapperStyle(null);
        props.setTextStyle(null);
        props.setInputStyle(null);
        props.setUrl(null);
        props.setStyle(null);

        // Act: Сериализуем и десериализуем
        String json = objectMapper.writeValueAsString(props);
        SectionElementProps deserializedProps = objectMapper.readValue(json, SectionElementProps.class);

        // Assert: Поля с null корректно обрабатываются
        assertNotNull(deserializedProps);
        assertEquals("keyWithNulls", deserializedProps.getKey());
        assertNull(deserializedProps.getText());
        assertNull(deserializedProps.getWrapperStyle());
        assertNull(deserializedProps.getTextStyle());
        assertNull(deserializedProps.getInputStyle());
        assertNull(deserializedProps.getUrl());
        assertNull(deserializedProps.getStyle());
    }

    @Test
    @DisplayName("Сохранение пользовательского UUID при сериализации и десериализации")
    public void testCustomIdPreserved() throws JsonProcessingException {
        // Arrange: создаём объект с кастомным UUID
        UUID customId = UUID.randomUUID();
        SectionElementProps props = new SectionElementProps();
        props.setId(customId);

        // Act: Сериализуем и десериализуем
        String json = objectMapper.writeValueAsString(props);
        SectionElementProps deserializedProps = objectMapper.readValue(json, SectionElementProps.class);

        // Assert: UUID сохраняется корректно
        assertNotNull(deserializedProps);
        assertEquals(customId, deserializedProps.getId());
    }

    @Test
    @DisplayName("Сериализация объекта с пустыми Map")
    public void testEmptyMapSerialization() throws JsonProcessingException {
        // Arrange: создаём объект с пустыми Map
        SectionElementProps props = new SectionElementProps(
                "emptyMapsKey",
                "Empty maps test",
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                "https://example.com",
                new HashMap<>()
        );

        // Act: Сериализуем и десериализуем
        String json = objectMapper.writeValueAsString(props);
        SectionElementProps deserializedProps = objectMapper.readValue(json, SectionElementProps.class);

        // Assert: Пустые Map корректно обрабатываются
        assertNotNull(deserializedProps);
        assertTrue(deserializedProps.getWrapperStyle().isEmpty());
        assertTrue(deserializedProps.getTextStyle().isEmpty());
        assertTrue(deserializedProps.getInputStyle().isEmpty());
        assertTrue(deserializedProps.getStyle().isEmpty());
    }
}
