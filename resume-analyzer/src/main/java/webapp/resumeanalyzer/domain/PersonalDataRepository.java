package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, UUID> {

    @Query("SELECT p FROM PersonalData p WHERE lower(p.full_name) LIKE lower(concat('%', :full_name,'%'))")
    List<PersonalData> findAllByFull_nameContainingIgnoreCase(@Param("full_name") String full_name);
}