package ru.kata.project.resumegenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kata.project.resumegenerator.domain.BlockElement;
import ru.kata.project.resumegenerator.domain.BlockElementRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Unit-тесты для BlockElementRepository")
class BlockElementRepositoryTest {

    @Autowired
    private BlockElementRepository repository;

    @Test
    @DisplayName("Сохранение и загрузка элемента")
    void saveAndFindByIdTest() {
        // Создаём тестовый элемент
        BlockElement element = new BlockElement();
        element.setName("Test Block");
        element.setTitle("Test Title");
        element.setType("Test Type");
        element.setSource("Test Source");

        // Сохраняем элемент
        BlockElement savedElement = repository.save(element);

        // Проверяем, что элемент сохранён
        assertThat(savedElement.getId()).isNotNull();
        assertThat(savedElement.getName()).isEqualTo("Test Block");

        // Загружаем элемент по ID
        BlockElement foundElement = repository.findById(savedElement.getId()).orElse(null);

        // Проверяем, что элемент загружен корректно
        assertThat(foundElement).isNotNull();
        assertThat(foundElement.getName()).isEqualTo("Test Block");
    }

    @Test
    @DisplayName("Поиск элементов по имени")
    void findByNameTest() {
        // Создаём и сохраняем элементы
        BlockElement element1 = new BlockElement();
        element1.setName("Block A");
        repository.save(element1);

        BlockElement element2 = new BlockElement();
        element2.setName("Block B");
        repository.save(element2);

        BlockElement element3 = new BlockElement();
        element3.setName("Block A");
        repository.save(element3);

        // Ищем элементы с именем "Block A"
        List<BlockElement> results = repository.findByName("Block A");

        // Проверяем, что найдено 2 элемента
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getName()).isEqualTo("Block A");
        assertThat(results.get(1).getName()).isEqualTo("Block A");
    }

    @Test
    @DisplayName("Удаление элемента")
    void deleteElementTest() {
        // Создаём тестовый элемент
        BlockElement element = new BlockElement();
        element.setName("Block to Delete");
        BlockElement savedElement = repository.save(element);

        // Удаляем элемент
        repository.delete(savedElement);

        // Проверяем, что элемент удалён
        boolean exists = repository.existsById(savedElement.getId());
        assertThat(exists).isFalse();
    }
}