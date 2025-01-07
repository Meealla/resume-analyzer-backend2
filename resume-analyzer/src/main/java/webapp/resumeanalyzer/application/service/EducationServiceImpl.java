package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.repository.EducationRepository;
import webapp.resumeanalyzer.domain.service.EducationService;


/**
 * Сервис CRUD методов сущности Education.
 */
@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    /**
     * Метод для внедрения зависимостей.
     */
    @Autowired
    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Transactional
    @Override
    public Education createEducation(Education education) {
        if (education.getName() == null || education.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (compareFrom_yearAndTo_Year(education)) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return educationRepository.save(education);
    }

    @Transactional
    @Override
    public Education updateEducation(String id, Education education) {
        UUID uuid = generateUuid(id);
        education.setId(uuid);
        Optional<Education> existingEducation = educationRepository.findById(uuid);
        if (existingEducation.isEmpty()) {
            throw new IllegalArgumentException("Education with uuid " + uuid + " not found.");
        }
        if (compareFrom_yearAndTo_Year(education)) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return educationRepository.save(education);
    }

    @Transactional
    @Override
    public void deleteEducation(String id) {
        UUID uuid = generateUuid(id);
        educationRepository.deleteById(uuid);
    }

    @Override
    public List<Education> loadEducationByNameFilter(String nameFilter) {
        if (nameFilter == null || nameFilter.isEmpty()) {
            return educationRepository.findAll();
        } else {
            return educationRepository.findAllByNameContainingIgnoreCase(nameFilter);
        }
    }

    @Override
    public Education getEducationById(String id) {
        UUID uuid = generateUuid(id);
        return educationRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Education with uuid " + uuid + " not found."));
    }

    private UUID generateUuid(String id) {
        return UUID.fromString(id);
    }

    private boolean compareFrom_yearAndTo_Year(Education education) {
        return Integer.parseInt(education.getFrom_year()) >= Integer.parseInt(
                education.getTo_year());
    }
}