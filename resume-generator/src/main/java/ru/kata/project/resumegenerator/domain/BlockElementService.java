package ru.kata.project.resumegenerator.domain;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlockElementService {

    private final BlockElementRepository blockElementRepository;

    @Autowired
    public BlockElementService(BlockElementRepository blockElementRepository) {
        this.blockElementRepository = blockElementRepository;
    }

    @Transactional
    public BlockElement createBlockElement(BlockElement blockElement) {
        if (blockElement.getName() == null || blockElement.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        List<BlockElement> existingBlock = blockElementRepository.findByName(blockElement.getName());
        if (existingBlock.size() > 0) {
            throw new IllegalArgumentException("Block with this name already exists.");
        }
        return blockElementRepository.save(blockElement);
    }

    @Transactional
    public BlockElement updateBlockElement(UUID id, BlockElement updatedBlockElement) {
        updatedBlockElement.setId(id); // Важно! Устанавливаем ID
        Optional<BlockElement> existingBlock = blockElementRepository.findById(id);
        if (existingBlock.isEmpty()) {
            throw new IllegalArgumentException("Block with id " + id + " not found.");
        }
        return blockElementRepository.save(updatedBlockElement);
    }

    @Transactional
    public void deleteBlockElement(UUID id) {
        blockElementRepository.deleteById(id);
    }


    public List<BlockElement> loadBlockElements(String nameFilter) {
        if (nameFilter == null || nameFilter.isEmpty()) {
            return blockElementRepository.findAll();
        } else {
            return blockElementRepository.findByName(nameFilter);
        }
    }

    public BlockElement getBlockElement(UUID id) {
        return blockElementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Block with id " + id + " not found."));
    }
}