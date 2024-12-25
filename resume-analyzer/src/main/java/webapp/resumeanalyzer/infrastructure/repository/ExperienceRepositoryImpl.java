package webapp.resumeanalyzer.infrastructure.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.Experience;

@Repository
public interface ExperienceRepositoryImpl extends JpaRepository<Experience, UUID> {

    @Query("SELECT e FROM Experience e WHERE lower(e.name) LIKE lower(concat('%', :name,'%'))")
    List<Experience> findAllByNameContainingIgnoreCase(@Param("name") String name);
}