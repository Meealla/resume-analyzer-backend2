package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Experience;
import webapp.resumeanalyzer.domain.repository.ExperienceRepository;
import webapp.resumeanalyzer.domain.service.ExperienceService;

/**
 * Сервис CRUD методов сущности Experience.
 */
@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    /**
     * Метод для внедрения зависимостей.
     */
    @Autowired
    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Transactional
    @Override
    public Experience createExperience(Experience experience) {
        if (experience.getName() == null || experience.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (compareFrom_yearAndTo_Year(experience)) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return experienceRepository.save(experience);
    }

    @Transactional
    @Override
    public Experience updateExperience(String id, Experience experience) {
        UUID uuid = generateUuid(id);
        experience.setId(uuid);
        Optional<Experience> existingExperience = experienceRepository.findById(uuid);
        if (existingExperience.isEmpty()) {
            throw new IllegalArgumentException("Experience with uuid " + uuid + " not found.");
        }
        if (compareFrom_yearAndTo_Year(experience)) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return experienceRepository.save(experience);
    }

    @Transactional
    @Override
    public void deleteExperience(String id) {
        UUID uuid = generateUuid(id);
        experienceRepository.deleteById(uuid);
    }

    @Override
    public List<Experience> loadExperienceByNameFilter(String nameFilter) {
        if (nameFilter == null || nameFilter.isEmpty()) {
            return experienceRepository.findAll();
        } else {
            return experienceRepository.findAllByNameContainingIgnoreCase(nameFilter);
        }
    }

    @Override
    public Experience getExperienceById(String id) {
        UUID uuid = generateUuid(id);
        return experienceRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Experience with uuid " + uuid + " not found."));
    }

    private UUID generateUuid(String id) {
        return UUID.fromString(id);
    }

    private boolean compareFrom_yearAndTo_Year(Experience experience) {
        return Integer.parseInt(experience.getFrom_year()) >= Integer.parseInt(
                experience.getTo_year());
    }
}