package infrastructure.repository;

import domain.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с коллекцией "templates" в MongoDB.
 * Данный интерфейс предоставляет методы для выполнения CRUD-операция над сущностью {@link Template}.
 */
@Repository
public interface TemplateRepository extends MongoRepository<Template, UUID> {

}
