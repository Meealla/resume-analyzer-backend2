package webapp.resumegenerator.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webapp.resumegenerator.domain.model.BlockElement;
import webapp.resumegenerator.domain.repository.BlockElementRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * Сервисный слой для сущности BlockElement
 */
@Service
public class BlockElementService {

    private final BlockElementRepository repository;

    public BlockElementService(BlockElementRepository repository) {
        this.repository = repository;
    }

    // Создание нового блока
    @Transactional
    public BlockElement createBlock(BlockElement blockElement) {
        if (repository.existsByName(blockElement.getName())) {
            throw new IllegalArgumentException("Block with name '" + blockElement.getName() + "' already exists");
        }
        return repository.save(blockElement);
    }

    // Обновление существующего блока
    @Transactional
    public BlockElement updateBlock(UUID id, BlockElement updatedBlock) {
        BlockElement existingBlock = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Block with ID '" + id + "' not found"));

        // Обновляем поля
        existingBlock.setName(updatedBlock.getName());
        existingBlock.setTitle(updatedBlock.getTitle());
        existingBlock.setType(updatedBlock.getType());
        existingBlock.setSource(updatedBlock.getSource());
        existingBlock.setColumns(updatedBlock.getColumns());
        existingBlock.setProps(updatedBlock.getProps());
        existingBlock.setChildren(updatedBlock.getChildren());
        existingBlock.setLayout(updatedBlock.getLayout());

        return repository.save(existingBlock);
    }

    // Удаление блока
    @Transactional
    public void deleteBlock(UUID id) {
        BlockElement blockElement = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Block with ID '" + id + "' not found"));

        repository.delete(blockElement);
    }

    // Получение всех блоков
    public List<BlockElement> getAllBlocks() {
        return repository.findAll();
    }

    // Поиск блоков по названию
    public List<BlockElement> getBlocksByName(String name) {
        return repository.findByName(name);
    }

    // Получение блока по ID
    public Optional<BlockElement> getBlockById(UUID id) {
        return repository.findById(id);
    }
}
