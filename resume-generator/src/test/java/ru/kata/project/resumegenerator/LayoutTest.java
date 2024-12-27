package ru.kata.project.resumegenerator;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import webapp.resumegenerator.domain.model.Layout;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("тест для проверки класса Layout")
public class LayoutTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("проверка на сериализацию/десериализацию")
    void testLayoutSerializationAndDeserialization() throws JsonProcessingException {
        Layout layout = createSampleLayout();
        String jsonString = objectMapper.writeValueAsString(layout);
        Layout deserializedLayout = objectMapper.readValue(jsonString, Layout.class);
        assertEquals(layout.getI(), deserializedLayout.getI());
        assertEquals(layout.getX(), deserializedLayout.getX());
        assertEquals(layout.getY(), deserializedLayout.getY());
        assertEquals(layout.getW(), deserializedLayout.getW());
        assertEquals(layout.getH(), deserializedLayout.getH());
        assertEquals(layout.getMinW(), deserializedLayout.getMinW());
        assertEquals(layout.getMaxW(), deserializedLayout.getMaxW());
        assertEquals(layout.getMinH(), deserializedLayout.getMinH());
        assertEquals(layout.getMaxH(), deserializedLayout.getMaxH());
        assertEquals(layout.getMoved(), deserializedLayout.getMoved());
        assertEquals(layout.getIsStatic(), deserializedLayout.getIsStatic());
        assertEquals(layout.getIsDraggable(), deserializedLayout.getIsDraggable());
        assertEquals(layout.getIsResizable(), deserializedLayout.getIsResizable());
        assertEquals(layout.getResizeHandles(), deserializedLayout.getResizeHandles());
        assertEquals(layout.getIsBounded(), deserializedLayout.getIsBounded());
    }

    @Test
    @DisplayName("проверка на нулевое значение")
    void testLayoutSerializationAndDeserialization_NullValues() throws JsonProcessingException {
        Layout layout = new Layout(); // используем конструктор по умолчанию для null значений
        String jsonString = objectMapper.writeValueAsString(layout);
        Layout deserializedLayout = objectMapper.readValue(jsonString, Layout.class);
        assertNull(deserializedLayout.getId());
        assertNull(deserializedLayout.getI());
        assertEquals(0, deserializedLayout.getX());
        assertEquals(0, deserializedLayout.getY());
        assertEquals(0, deserializedLayout.getW());
        assertEquals(0, deserializedLayout.getH());
        assertNull(deserializedLayout.getMinW());
        assertNull(deserializedLayout.getMaxW());
        assertNull(deserializedLayout.getMinH());
        assertNull(deserializedLayout.getMaxH());
        assertNull(deserializedLayout.getMoved());
        assertNull(deserializedLayout.getIsStatic());
        assertNull(deserializedLayout.getIsDraggable());
        assertNull(deserializedLayout.getIsResizable());
        assertNull(deserializedLayout.getResizeHandles());
        assertNull(deserializedLayout.getIsBounded());
    }

    private Layout createSampleLayout() {
        List<String> handles = Arrays.asList("top", "bottom");
        return new Layout(UUID.randomUUID(), "1", 10, 20, 300, 150, 100, 400, 50, 200
                , true, false, true, true, handles, true);
    }
}