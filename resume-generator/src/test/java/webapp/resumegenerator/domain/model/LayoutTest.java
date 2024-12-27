package webapp.resumegenerator.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class LayoutTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Тест сериализации: проверка всех полей")
    void testSerializationWithAllFields() throws JsonProcessingException {
        UUID id = UUID.randomUUID();
        List<String> resizeHandles = Arrays.asList("left", "right");

        Layout layout = new Layout(
                id,
                "block1",
                10, 20, 5, 5,
                2, 10, 1, 8,
                false, true, true, false,
                resizeHandles, true
        );

        String json = objectMapper.writeValueAsString(layout);

        assertTrue(json.contains("\"id\":\"" + id + "\""), "UUID должен сериализоваться корректно");
        assertTrue(json.contains("\"i\":\"block1\""));
        assertTrue(json.contains("\"x\":10"));
        assertTrue(json.contains("\"y\":20"));
        assertTrue(json.contains("\"w\":5"));
        assertTrue(json.contains("\"h\":5"));
        assertTrue(json.contains("\"minW\":2"));
        assertTrue(json.contains("\"maxW\":10"));
        assertTrue(json.contains("\"minH\":1"));
        assertTrue(json.contains("\"maxH\":8"));
        assertTrue(json.contains("\"moved\":false"));
        assertTrue(json.contains("\"isStatic\":true"));
        assertTrue(json.contains("\"isDraggable\":true"));
        assertTrue(json.contains("\"isResizable\":false"));
        assertTrue(json.contains("\"resizeHandles\":[\"left\",\"right\"]"));
        assertTrue(json.contains("\"isBounded\":true"));
    }

    @Test
    @DisplayName("Тест десериализации: проверка всех полей")
    void testDeserializationWithAllFields() throws JsonProcessingException {
        String json = """
                {
                  "id": "123e4567-e89b-12d3-a456-426614174000",
                  "i": "block1",
                  "x": 10,
                  "y": 20,
                  "w": 5,
                  "h": 5,
                  "minW": 2,
                  "maxW": 10,
                  "minH": 1,
                  "maxH": 8,
                  "moved": false,
                  "isStatic": true,
                  "isDraggable": true,
                  "isResizable": false,
                  "resizeHandles": ["left", "right"],
                  "isBounded": true
                }
                """;

        Layout layout = objectMapper.readValue(json, Layout.class);

        assertEquals(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), layout.getId(), "UUID должен быть корректно десериализован");
        assertEquals("block1", layout.getI());
        assertEquals(10, layout.getX());
        assertEquals(20, layout.getY());
        assertEquals(5, layout.getW());
        assertEquals(5, layout.getH());
        assertEquals(2, layout.getMinW());
        assertEquals(10, layout.getMaxW());
        assertEquals(1, layout.getMinH());
        assertEquals(8, layout.getMaxH());
        assertFalse(layout.getMoved());
        assertTrue(layout.getStatic());
        assertTrue(layout.getDraggable());
        assertFalse(layout.getResizable());
        assertEquals(Arrays.asList("left", "right"), layout.getResizeHandles());
        assertTrue(layout.getBounded());
    }

    @Test
    @DisplayName("Тест сериализации: null-поля должны исключаться из JSON")
    void testSerializationWithNullFields() throws JsonProcessingException {
        Layout layout = new Layout(
                null, // id
                null, // i
                10, 20, 5, 5,
                null, null, null, null,
                null, null, null, null,
                null, null
        );

        String json = objectMapper.writeValueAsString(layout);

        assertFalse(json.contains("\"id\""), "Поле id должно отсутствовать");
        assertFalse(json.contains("\"i\""), "Поле i должно отсутствовать");
        assertFalse(json.contains("\"minW\""), "Поле minW должно отсутствовать");
        assertFalse(json.contains("\"maxW\""), "Поле maxW должно отсутствовать");
        assertFalse(json.contains("\"minH\""), "Поле minH должно отсутствовать");
        assertFalse(json.contains("\"maxH\""), "Поле maxH должно отсутствовать");
        assertFalse(json.contains("\"moved\""), "Поле moved должно отсутствовать");
        assertFalse(json.contains("\"isStatic\""), "Поле isStatic должно отсутствовать");
        assertFalse(json.contains("\"resizeHandles\""), "Поле resizeHandles должно отсутствовать");
    }

    @Test
    @DisplayName("Тест десериализации: null-поля")
    void testDeserializationWithNullFields() throws JsonProcessingException {
        String json = """
                {
                  "x": 10,
                  "y": 20,
                  "w": 5,
                  "h": 5
                }
                """;

        Layout layout = objectMapper.readValue(json, Layout.class);

        assertNull(layout.getId(), "Поле id должно быть null");
        assertNull(layout.getI(), "Поле i должно быть null");
        assertNull(layout.getMinW(), "Поле minW должно быть null");
        assertNull(layout.getMaxW(), "Поле maxW должно быть null");
        assertNull(layout.getMinH(), "Поле minH должно быть null");
        assertNull(layout.getMaxH(), "Поле maxH должно быть null");
        assertNull(layout.getMoved(), "Поле moved должно быть null");
        assertNull(layout.getStatic(), "Поле isStatic должно быть null");
        assertNull(layout.getResizeHandles(), "Поле resizeHandles должно быть null");
    }

    @Test
    @DisplayName("Тест пользовательского UUID: должен сохраняться при сериализации и десериализации")
    void testCustomUUIDSerializationAndDeserialization() throws JsonProcessingException {
        UUID customId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        Layout layout = new Layout();
        layout.setId(customId);

        String json = objectMapper.writeValueAsString(layout);
        assertTrue(json.contains("\"id\":\"123e4567-e89b-12d3-a456-426614174000\""), "UUID должен быть корректно сериализован");

        Layout deserializedLayout = objectMapper.readValue(json, Layout.class);
        assertEquals(customId, deserializedLayout.getId(), "UUID должен быть корректно десериализован");
    }
}