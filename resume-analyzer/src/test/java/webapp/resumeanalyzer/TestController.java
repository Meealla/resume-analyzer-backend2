package webapp.resumeanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;
import webapp.resumeanalyzer.domain.TestEntity;

@RestController
public class TestController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        try {
            TestEntity entity = entityManager.createQuery(
                            "SELECT t FROM TestEntity t WHERE t.id = 1", TestEntity.class)
                    .getSingleResult();
            response.put("result", entity.getTestData());
        } catch (NoResultException e) {
            response.put("error", "Entity not found");
        } catch (Exception e) {
            response.put("error", "Database error: " + e.getMessage());
        }
        return response;
    }
}