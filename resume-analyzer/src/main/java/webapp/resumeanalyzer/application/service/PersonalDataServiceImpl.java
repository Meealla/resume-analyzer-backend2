package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.repository.PersonalDataRepository;
import webapp.resumeanalyzer.domain.service.PersonalDataService;

/**
 * Сервис CRUD методов сущности PersonalData.
 */
@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private final PersonalDataRepository personalDataRepository;

    /**
     * Метод для внедрения зависимостей.
     */
    @Autowired
    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }

    @Transactional
    @Override
    public PersonalData createPersonalData(PersonalData personalData) {
        if (personalData.getFull_name() == null || personalData.getFull_name().isEmpty()) {
            throw new IllegalArgumentException("Full_name cannot be null or empty.");
        }
        return personalDataRepository.save(personalData);
    }

    @Transactional
    @Override
    public PersonalData updatePersonalData(String id, PersonalData personalData) {
        UUID uuid = generateUuid(id);
        personalData.setId(uuid);
        Optional<PersonalData> existingPersonalData = personalDataRepository.findById(uuid);
        if (existingPersonalData.isEmpty()) {
            throw new IllegalArgumentException("PersonalData with uuid " + uuid + " not found.");
        }
        return personalDataRepository.save(personalData);
    }

    @Transactional
    @Override
    public void deletePersonalData(String id) {
        UUID uuid = generateUuid(id);
        personalDataRepository.deleteById(uuid);
    }

    @Override
    public List<PersonalData> loadPersonalDataByNameFilter(String personalDataFilter) {
        if (personalDataFilter == null || personalDataFilter.isEmpty()) {
            return personalDataRepository.findAll();
        } else {
            return personalDataRepository.findAllByFull_nameContainingIgnoreCase(
                    personalDataFilter);
        }
    }

    @Override
    public PersonalData getPersonalDataById(String id) {
        UUID uuid = generateUuid(id);
        return personalDataRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException(
                        "PersonalData with uuid " + uuid + " not found."));
    }

    private UUID generateUuid(String id) {
        return UUID.fromString(id);
    }
}