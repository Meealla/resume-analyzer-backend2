package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;

public interface PersonalDataService {

    PersonalData createPersonalData(PersonalData personalData);

    PersonalData updatePersonalData(UUID uuid, PersonalData personalData);

    void deletePersonalData(UUID uuid);

    List<PersonalData> loadPersonalData(String personalDataFilter);

    PersonalData getPersonalData(UUID uuid);
}
