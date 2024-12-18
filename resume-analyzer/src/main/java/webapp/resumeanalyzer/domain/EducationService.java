package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;

public interface EducationService {

    Education createEducation(Education education);

    Education updateEducation(UUID uuid, Education education);

    void deleteEducation(UUID uuid);

    List<Education> loadEducation(String nameFilter);

    Education getEducation(UUID uuid);
}
