package webapp.resumebuilder.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import webapp.resumebuilder.domain.model.BlockElement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlockElementRepositoryTest {

    @Mock
    private BlockElementRepository repository;

    private BlockElement blockElement;
    private UUID blockId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        blockId = UUID.randomUUID();
        blockElement = new BlockElement();
        blockElement.setId(blockId);
        blockElement.setName("Test Block");
        blockElement.setTitle("Test Title");
    }

    @Test
    @DisplayName("Поиск по имени: должен вернуть список блоков с заданным именем")
    void findByName_shouldReturnBlocksWithName() {
        // Mock data
        List<BlockElement> blocks = List.of(blockElement);
        when(repository.findByName("Test Block")).thenReturn(blocks);

        // Act
        List<BlockElement> result = repository.findByName("Test Block");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(blockElement, result.get(0));
        verify(repository).findByName("Test Block");
    }

    @Test
    @DisplayName("Проверка уникальности имени: должен вернуть true, если блок с таким именем существует")
    void existsByName_shouldReturnTrueWhenNameExists() {
        // Mock behavior
        when(repository.existsByName("Test Block")).thenReturn(true);

        // Act
        boolean exists = repository.existsByName("Test Block");

        // Assert
        assertTrue(exists);
        verify(repository).existsByName("Test Block");
    }

    @Test
    @DisplayName("Проверка уникальности имени: должен вернуть false, если блок с таким именем не существует")
    void existsByName_shouldReturnFalseWhenNameDoesNotExist() {
        // Mock behavior
        when(repository.existsByName("Non-Existent Block")).thenReturn(false);

        // Act
        boolean exists = repository.existsByName("Non-Existent Block");

        // Assert
        assertFalse(exists);
        verify(repository).existsByName("Non-Existent Block");
    }

    @Test
    @DisplayName("Поиск по ID: должен вернуть блок, если блок существует")
    void findById_shouldReturnBlockWhenExists() {
        // Mock behavior
        when(repository.findById(blockId)).thenReturn(Optional.of(blockElement));

        // Act
        Optional<BlockElement> result = repository.findById(blockId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(blockElement, result.get());
        verify(repository).findById(blockId);
    }

    @Test
    @DisplayName("Поиск по ID: должен вернуть пустой Optional, если блок не существует")
    void findById_shouldReturnEmptyWhenNotExists() {
        // Mock behavior
        when(repository.findById(blockId)).thenReturn(Optional.empty());

        // Act
        Optional<BlockElement> result = repository.findById(blockId);

        // Assert
        assertFalse(result.isPresent());
        verify(repository).findById(blockId);
    }

    @Test
    @DisplayName("Сохранение блока: должен сохранить блок и вернуть его")
    void save_shouldSaveBlockAndReturn() {
        // Mock behavior
        when(repository.save(blockElement)).thenReturn(blockElement);

        // Act
        BlockElement savedBlock = repository.save(blockElement);

        // Assert
        assertNotNull(savedBlock);
        assertEquals(blockElement, savedBlock);
        verify(repository).save(blockElement);
    }

    @Test
    @DisplayName("Удаление блока: должен вызвать метод delete")
    void delete_shouldDeleteBlock() {
        // Act
        repository.delete(blockElement);

        // Assert
        verify(repository).delete(blockElement);
    }
}
