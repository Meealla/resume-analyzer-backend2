package webapp.resumegenerator.domain.repository;

import webapp.resumegenerator.domain.model.Template;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Реализация пользовательского репозитория для работы с коллекцией шаблонов в MongoDB.
 */
@Repository
public class CustomTemplateRepositoryImpl implements CustomTemplateRepository {
    private final MongoTemplate mongoTemplate;

    /**
     * Конструктор для инъекции зависимости.
     *
     * @param mongoTemplate Экземпляр MongoTemplate для взаимодействия с БД.
     */
    @Autowired
    public CustomTemplateRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Поиск шаблонов по имени.
     *
     * @param name Имя шаблона для поиска.
     * @return Список шаблонов,  имя которых соответствует заданному.
     */
    @Override
    public List<Template> findByName(String name) {
        Query query = new Query(Criteria.where("name").regex(name, "i"));
        return mongoTemplate.find(query, Template.class);
    }

    /**
     * Поиск шаблонов по дате.
     *
     * @param startDate Начальная дата диапазона.
     * @param endDate Конечная дата диапазона.
     * @return Список шаблонов, входящих в указанный диапазон дат.
     */
    @Override
    public List<Template> findByDate(LocalDate startDate, LocalDate endDate) {
        Query query = new Query(Criteria.where("date").gte(startDate).lt(endDate));
        return mongoTemplate.find(query, Template.class);
    }
}