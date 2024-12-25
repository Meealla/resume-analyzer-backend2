package webapp.resumeanalyzer.infrastructure.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.resumeanalyzer.domain.model.SocialLink;

@Repository
public interface SocialLinkRepositoryImpl extends JpaRepository<SocialLink, UUID> {

    @Query("SELECT s FROM SocialLink s WHERE lower(s.name) LIKE lower(concat('%', :name,'%'))")
    List<SocialLink> findAllByNameContainingIgnoreCase(@Param("name") String name);
}