package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, UUID> {

    @Query("SELECT h FROM Hobby h WHERE lower(h.hobby) LIKE lower(concat('%', :hobby,'%'))")
    List<Hobby> findAllByHobbyContainingIgnoreCase(@Param("hobby") String hobby);
}