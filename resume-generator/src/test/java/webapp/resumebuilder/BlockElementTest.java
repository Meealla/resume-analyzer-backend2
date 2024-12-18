package webapp.resumebuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webapp.resumebuilder.domain.model.BlockElement;
import webapp.resumebuilder.domain.model.Layout;
import webapp.resumebuilder.domain.model.SectionElementProps;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса BlockElement")
public class BlockElementTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // Для читаемого JSON

    @Test
    @DisplayName("Сериализация и десериализация объекта BlockElement")
    public void testSerializationAndDeserialization() throws JsonProcessingException {
        // Arrange: создаём тестовый объект
        SectionElementProps props = new SectionElementProps(); // Пустой props для примера
        Layout layout = new Layout(); // Пустой layout для примера
        BlockElement childElement = new BlockElement(
                "childName", "childTitle", "childType", "childSource", 2, null, null, null
        );
        BlockElement blockElement = new BlockElement(
                "name", "title", "type", "source", 3, props, List.of(childElement), layout
        );

        // Act: Сериализуем и десериализуем объект
        String json = objectMapper.writeValueAsString(blockElement);
        BlockElement deserializedElement = objectMapper.readValue(json, BlockElement.class);

        // Assert: Проверяем, что объекты равны
        assertNotNull(deserializedElement);
        assertEquals(blockElement.getName(), deserializedElement.getName());
        assertEquals(blockElement.getTitle(), deserializedElement.getTitle());
        assertEquals(blockElement.getType(), deserializedElement.getType());
        assertEquals(blockElement.getSource(), deserializedElement.getSource());
        assertEquals(blockElement.getColumns(), deserializedElement.getColumns());
        assertNotNull(deserializedElement.getProps());
        assertNotNull(deserializedElement.getChildren());
        assertEquals(1, deserializedElement.getChildren().size());
    }

    @Test
    @DisplayName("Сериализация объекта BlockElement с null-полями")
    public void testSerializationWithNulls() throws JsonProcessingException {
        // Arrange: создаём объект с null-полями
        BlockElement blockElement = new BlockElement();

        // Act: Сериализуем объект
        String json = objectMapper.writeValueAsString(blockElement);
        BlockElement deserializedElement = objectMapper.readValue(json, BlockElement.class);

        // Assert: Проверяем, что после десериализации поля равны
        assertNotNull(deserializedElement);
        assertNull(deserializedElement.getName());
        assertNull(deserializedElement.getTitle());
        assertNull(deserializedElement.getType());
        assertNull(deserializedElement.getSource());
        assertNull(deserializedElement.getProps());
        assertNull(deserializedElement.getChildren());
        assertNull(deserializedElement.getLayout());
    }

    @Test
    @DisplayName("Сохранение пользовательского UUID при сериализации и десериализации")
    public void testSerializationWithCustomId() throws JsonProcessingException {
        // Arrange: Создаём объект с заранее заданным ID
        UUID customId = UUID.randomUUID();
        BlockElement blockElement = new BlockElement();
        blockElement.setId(customId);

        // Act: Сериализуем и десериализуем
        String json = objectMapper.writeValueAsString(blockElement);
        BlockElement deserializedElement = objectMapper.readValue(json, BlockElement.class);

        // Assert: ID совпадает
        assertNotNull(deserializedElement);
        assertEquals(customId, deserializedElement.getId());
    }
}