package webapp.resumeanalyzer.domain.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.model.ResumeFile;

/**
 * Интерфейс репозитория для выполнения CRUD-операций для сущности ResumeFile в БД.
 *
 *  TODO: Сделать поиск по пользователю
 */

@Repository
public interface ResumeFileRepository extends JpaRepository<ResumeFile, UUID> {

    //метод для поиска по имени
    @Query("SELECT r FROM ResumeFile r WHERE lower(r.filename) = lower(concat('%', :filename,'%'))")
    ResumeFile findByFilenameContainingIgnoreCase(@Param("filename") String filename);

    //метод для поиска по владельцу (В дальнейшем заменить на сущность пользователя)
    @Query("SELECT r FROM ResumeFile r WHERE lower(r.owner) LIKE lower(concat('%', :owner,'%'))")
    List<ResumeFile> findByOwnerContainingIgnoreCase(@Param("owner") String owner);


}
