package webapp.resumeanalyzer.infrastructure.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.Resume;

@Repository
public interface ResumeRepositoryImpl extends JpaRepository<Resume, UUID> {

    @Query("SELECT DISTINCT r.id FROM Resume r " + "JOIN Experience e ON r.id = e.id WHERE "
            + "(:keyword IS lower(r.experiences) LIKE lower(concat('%', :keyword,'%')))")
    List<Resume> findByKeywordIgnoreCase(@Param("keyword") String keyword);
}
