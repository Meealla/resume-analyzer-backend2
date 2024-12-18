package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;

public interface ExperienceService {

    Experience createExperience(Experience experience);

    Experience updateExperience(UUID uuid, Experience experience);

    void deleteExperience(UUID uuid);

    List<Experience> loadExperience(String nameFilter);

    Experience getExperience(UUID uuid);
}
