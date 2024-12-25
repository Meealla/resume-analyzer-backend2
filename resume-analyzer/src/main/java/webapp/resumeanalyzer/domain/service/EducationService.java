package webapp.resumeanalyzer.domain.service;

import java.util.List;
import java.util.UUID;
import webapp.resumeanalyzer.domain.model.Education;

public interface EducationService {

    Education createEducation(Education education);

    Education updateEducation(String id, Education education);

    void deleteEducation(String id);

    List<Education> loadEducationByNameFilter(String nameFilter);

    Education getEducation(String id);
}
