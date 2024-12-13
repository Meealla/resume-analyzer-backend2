package ru.kata.project.resumegenerator;

import ru.kata.project.resumegenerator.domain.BlockElement;
import ru.kata.project.resumegenerator.domain.BlockElementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat; // Using AssertJ for assertions

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class BlockElementRepositoryTest {

    @Autowired
    public final BlockElementRepository repository;

    public BlockElementRepositoryTest(BlockElementRepository repository) {
        this.repository = repository;
    }

    @Test
    public void testFindByName_existingName() {

        BlockElement block1 = new BlockElement();
        block1.setName("TestBlock");
        block1.setId(UUID.randomUUID());

        repository.save(block1);

        List<BlockElement> foundBlocks = repository.findByName("TestBlock");

        assertThat(foundBlocks).hasSize(1);
        assertThat(foundBlocks.get(0).getName()).isEqualTo("TestBlock");
    }

    @Test
    public void testFindByName_nonExistingName() {
        List<BlockElement> foundBlocks = repository.findByName("NonExistingBlock");
        assertThat(foundBlocks).isEmpty(); // Correct assertion
    }

    @Test
    public void testSave() {
        BlockElement block = new BlockElement();
        block.setName("TestBlock");
        block.setId(UUID.randomUUID());

        repository.save(block);

        BlockElement savedBlock = repository.findById(block.getId()).get(); // Use findById to verify
        assertThat(savedBlock).isNotNull();
        assertThat(savedBlock.getName()).isEqualTo("TestBlock");
    }

    @Test
    public void testFindAll(){
        BlockElement block1 = new BlockElement();
        block1.setName("TestBlock1");
        block1.setId(UUID.randomUUID());

        BlockElement block2 = new BlockElement();
        block2.setName("TestBlock2");
        block2.setId(UUID.randomUUID());

        repository.saveAll(List.of(block1, block2));

        List<BlockElement> allBlocks = repository.findAll();
        assertThat(allBlocks).hasSize(2);
    }
}

