package ru.kata.project.resumegenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kata.project.resumegenerator.domain.BlockElement;
import ru.kata.project.resumegenerator.domain.BlockElementRepository;
import ru.kata.project.resumegenerator.domain.BlockElementService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Unit-тесты для BlockElementService")
class BlockElementServiceTest {

    @Mock
    private BlockElementRepository blockElementRepository;

    @InjectMocks
    private BlockElementService blockElementService;

    private BlockElement blockElement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        blockElement = new BlockElement();
        blockElement.setId(UUID.randomUUID());
        blockElement.setName("Test Block");
        blockElement.setTitle("Test Title");
    }

    @Test
    void createBlockElement_Success() {
        when(blockElementRepository.findByName(blockElement.getName())).thenReturn(List.of());
        when(blockElementRepository.save(blockElement)).thenReturn(blockElement);

        BlockElement createdBlock = blockElementService.createBlockElement(blockElement);

        assertNotNull(createdBlock);
        assertEquals(blockElement.getName(), createdBlock.getName());
        verify(blockElementRepository, times(1)).save(blockElement);
    }

    @Test
    void createBlockElement_NameIsNull_ShouldThrowException() {
        blockElement.setName(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> blockElementService.createBlockElement(blockElement));
        assertEquals("Name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void createBlockElement_NameAlreadyExists_ShouldThrowException() {
        when(blockElementRepository.findByName(blockElement.getName())).thenReturn(List.of(blockElement));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> blockElementService.createBlockElement(blockElement));
        assertEquals("Block with this name already exists.", exception.getMessage());
    }

    @Test
    void updateBlockElement_Success() {
        UUID id = UUID.randomUUID();
        blockElement.setId(id);
        when(blockElementRepository.findById(id)).thenReturn(Optional.of(blockElement));
        when(blockElementRepository.save(blockElement)).thenReturn(blockElement);

        BlockElement updatedBlock = blockElementService.updateBlockElement(id, blockElement);

        assertNotNull(updatedBlock);
        assertEquals(id, updatedBlock.getId());
        verify(blockElementRepository, times(1)).save(blockElement);
    }

    @Test
    void updateBlockElement_BlockNotFound_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        when(blockElementRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> blockElementService.updateBlockElement(id, blockElement));
        assertEquals("Block with id " + id + " not found.", exception.getMessage());
    }

    @Test
    void deleteBlockElement_Success() {
        UUID id = UUID.randomUUID();

        doNothing().when(blockElementRepository).deleteById(id);

        blockElementService.deleteBlockElement(id);

        verify(blockElementRepository, times(1)).deleteById(id);
    }

    @Test
    void loadBlockElements_NoFilter() {
        when(blockElementRepository.findAll()).thenReturn(List.of(blockElement));

        List<BlockElement> blockElements = blockElementService.loadBlockElements("");

        assertEquals(1, blockElements.size());
        verify(blockElementRepository, times(1)).findAll();
    }

    @Test
    void loadBlockElements_WithFilter() {
        when(blockElementRepository.findByName("Test Block")).thenReturn(List.of(blockElement));

        List<BlockElement> blockElements = blockElementService.loadBlockElements("Test Block");

        assertEquals(1, blockElements.size());
        verify(blockElementRepository, times(1)).findByName("Test Block");
    }

    @Test
    void getBlockElement_Success() {
        UUID id = UUID.randomUUID();
        when(blockElementRepository.findById(id)).thenReturn(Optional.of(blockElement));

        BlockElement retrievedBlock = blockElementService.getBlockElement(id);

        assertNotNull(retrievedBlock);
        assertEquals(blockElement.getName(), retrievedBlock.getName());
        verify(blockElementRepository, times(1)).findById(id);
    }

    @Test
    void getBlockElement_BlockNotFound_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        when(blockElementRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> blockElementService.getBlockElement(id));
        assertEquals("Block with id " + id + " not found.", exception.getMessage());
    }
}
