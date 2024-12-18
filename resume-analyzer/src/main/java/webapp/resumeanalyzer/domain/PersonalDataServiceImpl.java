package webapp.resumeanalyzer.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private final PersonalDataRepository personalDataRepository;

    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }

    @Transactional
    @Override
    public PersonalData createPersonalData(PersonalData personalData) {
        if (personalData.getFull_name() == null
                || personalData.getFull_name().isEmpty()) {
            throw new IllegalArgumentException("Full_name cannot be null or empty.");
        }
        return personalDataRepository.save(personalData);
    }

    @Transactional
    @Override
    public PersonalData updatePersonalData(UUID uuid, PersonalData personalData) {
        personalData.setId(uuid);
        Optional<PersonalData> existingPersonalData = personalDataRepository.findById(uuid);
        if (existingPersonalData.isEmpty()) {
            throw new IllegalArgumentException("PersonalData with uuid " + uuid + " not found.");
        }
        return personalDataRepository.save(personalData);
    }

    @Transactional
    @Override
    public void deletePersonalData(UUID uuid) {
        personalDataRepository.deleteById(uuid);
    }

    @Override
    public List<PersonalData> loadPersonalData(String personalDataFilter) {
        if (personalDataFilter == null || personalDataFilter.isEmpty()) {
            return personalDataRepository.findAll();
        } else {
            return personalDataRepository.findAllByFull_nameContainingIgnoreCase(
                    personalDataFilter);
        }
    }

    @Override
    public PersonalData getPersonalData(UUID uuid) {
        return personalDataRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException(
                        "PersonalData with uuid " + uuid + " not found."));
    }
}