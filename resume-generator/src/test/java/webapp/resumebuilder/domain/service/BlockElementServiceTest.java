package webapp.resumebuilder.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webapp.resumebuilder.domain.model.BlockElement;
import webapp.resumebuilder.domain.repository.BlockElementRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlockElementServiceTest {

    @Mock
    private BlockElementRepository repository;

    @InjectMocks
    private BlockElementService service;

    private BlockElement blockElement;
    private UUID blockId;

    @BeforeEach
    void setUp() {
        blockId = UUID.randomUUID();
        blockElement = new BlockElement();
        blockElement.setId(blockId);
        blockElement.setName("Test Block");
        blockElement.setTitle("Test Title");
    }

    @Test
    @DisplayName("Создание блока: должен сохранить блок, если блок с таким именем не существует")
    void createBlock_shouldSaveBlock_whenBlockDoesNotExist() {
        when(repository.existsByName(blockElement.getName())).thenReturn(false);
        when(repository.save(blockElement)).thenReturn(blockElement);

        BlockElement createdBlock = service.createBlock(blockElement);

        assertNotNull(createdBlock);
        assertEquals(blockElement.getName(), createdBlock.getName());
        verify(repository).existsByName(blockElement.getName());
        verify(repository).save(blockElement);
    }

    @Test
    @DisplayName("Создание блока: должен выбросить исключение, если блок с таким именем уже существует")
    void createBlock_shouldThrowException_whenBlockWithNameExists() {
        when(repository.existsByName(blockElement.getName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.createBlock(blockElement));

        assertEquals("Block with name 'Test Block' already exists", exception.getMessage());
        verify(repository).existsByName(blockElement.getName());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Обновление блока: должен обновить блок, если блок существует")
    void updateBlock_shouldUpdateBlock_whenBlockExists() {
        BlockElement updatedBlock = new BlockElement();
        updatedBlock.setName("Updated Block");

        when(repository.findById(blockId)).thenReturn(Optional.of(blockElement));
        when(repository.save(blockElement)).thenReturn(blockElement);

        BlockElement result = service.updateBlock(blockId, updatedBlock);

        assertNotNull(result);
        assertEquals("Updated Block", result.getName());
        verify(repository).findById(blockId);
        verify(repository).save(blockElement);
    }

    @Test
    @DisplayName("Обновление блока: должен выбросить исключение, если блок не существует")
    void updateBlock_shouldThrowException_whenBlockDoesNotExist() {
        when(repository.findById(blockId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.updateBlock(blockId, blockElement));

        assertEquals("Block with ID '" + blockId + "' not found", exception.getMessage());
        verify(repository).findById(blockId);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Удаление блока: должен удалить блок, если блок существует")
    void deleteBlock_shouldDeleteBlock_whenBlockExists() {
        when(repository.findById(blockId)).thenReturn(Optional.of(blockElement));

        service.deleteBlock(blockId);

        verify(repository).findById(blockId);
        verify(repository).delete(blockElement);
    }

    @Test
    @DisplayName("Удаление блока: должен выбросить исключение, если блок не существует")
    void deleteBlock_shouldThrowException_whenBlockDoesNotExist() {
        when(repository.findById(blockId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteBlock(blockId));

        assertEquals("Block with ID '" + blockId + "' not found", exception.getMessage());
        verify(repository).findById(blockId);
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Получение всех блоков: должен вернуть список всех блоков")
    void getAllBlocks_shouldReturnAllBlocks() {
        List<BlockElement> blocks = List.of(blockElement);
        when(repository.findAll()).thenReturn(blocks);

        List<BlockElement> result = service.getAllBlocks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(blockElement, result.get(0));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Поиск блоков по имени: должен вернуть список блоков с заданным именем")
    void getBlocksByName_shouldReturnBlocks_whenBlocksWithNameExist() {
        List<BlockElement> blocks = List.of(blockElement);
        when(repository.findByName(blockElement.getName())).thenReturn(blocks);

        List<BlockElement> result = service.getBlocksByName(blockElement.getName());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(blockElement, result.get(0));
        verify(repository).findByName(blockElement.getName());
    }

    @Test
    @DisplayName("Получение блока по ID: должен вернуть блок, если блок существует")
    void getBlockById_shouldReturnBlock_whenBlockExists() {
        when(repository.findById(blockId)).thenReturn(Optional.of(blockElement));

        Optional<BlockElement> result = service.getBlockById(blockId);

        assertTrue(result.isPresent());
        assertEquals(blockElement, result.get());
        verify(repository).findById(blockId);
    }

    @Test
    @DisplayName("Получение блока по ID: должен вернуть пустой Optional, если блок не существует")
    void getBlockById_shouldReturnEmpty_whenBlockDoesNotExist() {
        when(repository.findById(blockId)).thenReturn(Optional.empty());

        Optional<BlockElement> result = service.getBlockById(blockId);

        assertFalse(result.isPresent());
        verify(repository).findById(blockId);
    }
}
