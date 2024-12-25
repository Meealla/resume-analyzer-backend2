package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.service.PersonalDataService;
import webapp.resumeanalyzer.infrastructure.repository.PersonalDataRepositoryImpl;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private final PersonalDataRepositoryImpl personalDataRepositoryImpl;

    public PersonalDataServiceImpl(PersonalDataRepositoryImpl personalDataRepositoryImpl) {
        this.personalDataRepositoryImpl = personalDataRepositoryImpl;
    }

    @Transactional
    @Override
    public PersonalData createPersonalData(PersonalData personalData) {
        if (personalData.getFull_name() == null || personalData.getFull_name().isEmpty()) {
            throw new IllegalArgumentException("Full_name cannot be null or empty.");
        }
        return personalDataRepositoryImpl.save(personalData);
    }

    @Transactional
    @Override
    public PersonalData updatePersonalData(String id, PersonalData personalData) {
        UUID uuid = generateUUID(id);
        personalData.setId(uuid);
        Optional<PersonalData> existingPersonalData = personalDataRepositoryImpl.findById(uuid);
        if (existingPersonalData.isEmpty()) {
            throw new IllegalArgumentException("PersonalData with uuid " + uuid + " not found.");
        }
        return personalDataRepositoryImpl.save(personalData);
    }

    @Transactional
    @Override
    public void deletePersonalData(String id) {
        UUID uuid = generateUUID(id);
        personalDataRepositoryImpl.deleteById(uuid);
    }

    @Override
    public List<PersonalData> loadPersonalDataByNameFilter(String personalDataFilter) {
        if (personalDataFilter == null || personalDataFilter.isEmpty()) {
            return personalDataRepositoryImpl.findAll();
        } else {
            return personalDataRepositoryImpl.findAllByFull_nameContainingIgnoreCase(
                    personalDataFilter);
        }
    }

    @Override
    public PersonalData getPersonalData(String id) {
        UUID uuid = generateUUID(id);
        return personalDataRepositoryImpl.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException(
                        "PersonalData with uuid " + uuid + " not found."));
    }

    private UUID generateUUID(String id) {
        return UUID.fromString(id);
    }
}