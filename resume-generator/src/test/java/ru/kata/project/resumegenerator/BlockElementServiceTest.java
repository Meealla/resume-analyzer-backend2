package ru.kata.project.resumegenerator;

import domain.BlockElement;
import domain.BlockElementRepository;
import domain.BlockElementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockElementServiceTest {

    @Mock
    private BlockElementRepository blockElementRepository;
    @InjectMocks
    private BlockElementService blockElementService;
    private BlockElement blockElement;
    private UUID id;
    private BlockElement blockElement1;
    private BlockElement blockElement2;
    private UUID id1;
    private UUID id2;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        blockElement = new BlockElement();
        blockElement.setId(id);
        blockElement.setName("MyBlock");
        blockElement1 = new BlockElement();
        blockElement1.setId(id1);
        blockElement1.setName("Block1");
        blockElement2 = new BlockElement();
        blockElement2.setId(id2);
        blockElement2.setName("Block2");
    }

    @Test
    void createBlockElement_validInput_returnsBlockElement() {
        when(blockElementRepository.findByName(blockElement.getName())).thenReturn(Collections.emptyList());
        when(blockElementRepository.save(any(BlockElement.class))).thenReturn(blockElement);
        BlockElement createdBlock = blockElementService.createBlockElement(blockElement);
        assertNotNull(createdBlock);
        assertEquals(blockElement.getName(), createdBlock.getName());
        verify(blockElementRepository, times(1)).save(blockElement);
    }

    @Test
    void createBlockElement_duplicateName_throwsException() {
        when(blockElementRepository.findByName(blockElement.getName())).thenReturn(List.of(blockElement));
        assertThrows(IllegalArgumentException.class, () -> blockElementService.createBlockElement(blockElement));
    }

    @Test
    void createBlockElement_nullName_throwsException() {
        BlockElement blockElementNullName = new BlockElement();
        blockElementNullName.setName(null);
        assertThrows(IllegalArgumentException.class, () -> blockElementService.createBlockElement(blockElementNullName));
    }

    @Test
    void updateBlockElement_existingBlock_returnsUpdatedBlock() {
        BlockElement updatedBlockElement = new BlockElement();
        updatedBlockElement.setId(id);
        updatedBlockElement.setName("UpdatedBlock");

        when(blockElementRepository.findById(id)).thenReturn(Optional.of(blockElement));
        when(blockElementRepository.save(updatedBlockElement)).thenReturn(updatedBlockElement);

        BlockElement updatedBlock = blockElementService.updateBlockElement(id, updatedBlockElement);

        assertNotNull(updatedBlock);
        assertEquals(id, updatedBlock.getId());
        assertEquals("UpdatedBlock", updatedBlock.getName());
        verify(blockElementRepository, times(1)).save(updatedBlockElement);
    }

    @Test
    void updateBlockElement_nonExistingBlock_throwsException() {
        BlockElement updatedBlockElement = new BlockElement();
        updatedBlockElement.setName("UpdatedBlock");

        when(blockElementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> blockElementService.updateBlockElement(id, updatedBlockElement));
    }
//
//    @Test
//    void deleteBlockElement_existingBlock() {
//        when(blockElementRepository.findById(id)).thenReturn(Optional.of(blockElement));
//        blockElementService.deleteBlockElement(id);
//        verify(blockElementRepository).deleteById(id);
//    }
    @Test
    void deleteBlockElement_existingBlock() {
        // Убеждаемся, что блок существует
        when(blockElementRepository.findById(blockElement.getId())).thenReturn(Optional.of(blockElement));

        // Выполнение метода удаления
        blockElementService.deleteBlockElement(blockElement.getId());

        // Проверяем, что метод удаления был вызван на репозитории
        verify(blockElementRepository, times(1)).delete(blockElement);
    }

    @Test
    public void loadBlockElements_noFilter_returnsAll() {
        when(blockElementRepository.findAll()).thenReturn(List.of(blockElement1, blockElement2));

        List<BlockElement> loadedElements = blockElementService.loadBlockElements(null);

        assertThat(loadedElements).hasSize(2);
        assertThat(loadedElements).contains(blockElement1, blockElement2);
    }

    @Test
    public void loadBlockElements_withFilter_returnsMatching() {
        when(blockElementRepository.findByName("Block1")).thenReturn(List.of(blockElement1));

        List<BlockElement> loadedElements = blockElementService.loadBlockElements("Block1");

        assertThat(loadedElements).hasSize(1);
        assertThat(loadedElements).contains(blockElement1);
    }

    @Test
    public void loadBlockElements_withNonexistentFilter_returnsEmpty() {
        when(blockElementRepository.findByName("NonexistentBlock")).thenReturn(Collections.emptyList());

        List<BlockElement> loadedElements = blockElementService.loadBlockElements("NonexistentBlock");

        assertThat(loadedElements).isEmpty();
    }

    @Test
    public void getBlockElement_existingBlock_returnsBlock() {
        when(blockElementRepository.findById(id1)).thenReturn(Optional.of(blockElement1));

        BlockElement loadedElement = blockElementService.getBlockElement(id1);

        assertThat(loadedElement).isEqualTo(blockElement1);
    }

    @Test
    public void getBlockElement_nonExistingBlock_throwsException() {
        when(blockElementRepository.findById(id2)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> blockElementService.getBlockElement(id2));
    }
}
