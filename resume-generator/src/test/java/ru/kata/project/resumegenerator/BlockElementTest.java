package ru.kata.project.resumegenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import webapp.resumegenerator.domain.model.BlockElement;
import webapp.resumegenerator.domain.model.Layout;
import webapp.resumegenerator.domain.model.SectionElementProps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("тест для проверки класса BlockElement")

public class BlockElementTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("проверка на сериализацию/десериализацию")
    void testBlockElementSerializationAndDeserialization() throws JsonProcessingException {
        BlockElement block = createSampleBlockElement();
        String jsonString = objectMapper.writeValueAsString(block);
        BlockElement deserializedBlock = objectMapper.readValue(jsonString, BlockElement.class);

        assertEquals(block.getName(), deserializedBlock.getName());
        assertEquals(block.getTitle(), deserializedBlock.getTitle());
        assertEquals(block.getType(), deserializedBlock.getType());
        assertEquals(block.getSource(), deserializedBlock.getSource());
        assertEquals(block.getColumns(), deserializedBlock.getColumns());
        // Сравнение вложенных объектов
        assertEquals(block.getProps().getKey(), deserializedBlock.getProps().getKey()); // и т.д. для всех полей props
        assertEquals(block.getLayout().getI(), deserializedBlock.getLayout().getI()); // и т.д. для всех полей layout
        // Сравнение списка children (нужно добавить дополнительные проверки, если children не пустой)
        if(block.getChildren() != null) {
            assertEquals(block.getChildren().size(), deserializedBlock.getChildren().size());
        }
    }

    @Test
    @DisplayName("проверка на нулевое значение")
    void testBlockElementSerializationAndDeserialization_NullValues() throws JsonProcessingException {
        BlockElement block = new BlockElement();
        String jsonString = objectMapper.writeValueAsString(block);
        BlockElement deserializedBlock = objectMapper.readValue(jsonString, BlockElement.class);
        assertNull(deserializedBlock.getId());
        assertNull(deserializedBlock.getName());
        assertNull(deserializedBlock.getTitle());
        assertNull(deserializedBlock.getType());
        assertNull(deserializedBlock.getSource());
        assertNull(deserializedBlock.getColumns());
        assertNull(deserializedBlock.getProps());
        assertNull(deserializedBlock.getChildren());
        assertNull(deserializedBlock.getLayout());
    }


    private BlockElement createSampleBlockElement() {
        SectionElementProps props = new SectionElementProps(UUID.randomUUID(), "myKey", "My Text"
                , "wrapperStyle", "textStyle", "inputStyle", "http://example.com"
                , "style");
        Layout layout = new Layout(UUID.randomUUID(), "1", 10, 20, 300, 150, 100, 400, 50
                , 200, true, false, true, true, Arrays.asList("top", "bottom")
                , true);
        List<BlockElement> children = new ArrayList<>(); // можно добавить тестовые children
        return new BlockElement(UUID.randomUUID(), "My Block", "Block Title", "text", "some source"
                , 2, props, children, layout);
    }
}