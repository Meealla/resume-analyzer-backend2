package webapp.resumebuilder.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import webapp.resumebuilder.domain.model.BlockElement;

import java.util.List;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * интерфейс для управления сущностью BlockElement
 */
@Repository
public interface BlockElementRepository extends MongoRepository<BlockElement, UUID> {

    // Поиск блоков по названию
    @Query("{ 'name': ?0 }")
    List<BlockElement> findByName(String name);

    // Проверка на уникальность имени
    boolean existsByName(String name);
}
