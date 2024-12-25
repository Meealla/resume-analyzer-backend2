package webapp.resumeanalyzer.domain.service;

import java.util.List;
import java.util.UUID;
import webapp.resumeanalyzer.domain.model.Experience;

public interface ExperienceService {

    Experience createExperience(Experience experience);

    Experience updateExperience(String id, Experience experience);

    void deleteExperience(String id);

    List<Experience> loadExperienceByNameFilter(String nameFilter);

    Experience getExperience(String id);
}
