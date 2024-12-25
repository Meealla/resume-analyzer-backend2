package webapp.resumeanalyzer.domain.service;

import java.util.List;
import java.util.UUID;
import webapp.resumeanalyzer.domain.model.PersonalData;

public interface PersonalDataService {

    PersonalData createPersonalData(PersonalData personalData);

    PersonalData updatePersonalData(String id, PersonalData personalData);

    void deletePersonalData(String id);

    List<PersonalData> loadPersonalDataByNameFilter(String personalDataFilter);

    PersonalData getPersonalData(String id);
}
