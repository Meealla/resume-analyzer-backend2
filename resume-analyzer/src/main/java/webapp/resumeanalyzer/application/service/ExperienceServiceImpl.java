package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Experience;
import webapp.resumeanalyzer.domain.service.ExperienceService;
import webapp.resumeanalyzer.infrastructure.repository.ExperienceRepositoryImpl;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepositoryImpl experienceRepositoryImpl;

    @Autowired
    public ExperienceServiceImpl(ExperienceRepositoryImpl experienceRepositoryImpl) {
        this.experienceRepositoryImpl = experienceRepositoryImpl;
    }

    @Transactional
    @Override
    public Experience createExperience(Experience experience) {
        if (experience.getName() == null || experience.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (Integer.parseInt(experience.getFrom_year()) >= Integer.parseInt(
                experience.getTo_year())) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return experienceRepositoryImpl.save(experience);
    }

    @Transactional
    @Override
    public Experience updateExperience(String id, Experience experience) {
        UUID uuid = generateUUID(id);
        experience.setId(uuid);
        Optional<Experience> existingExperience = experienceRepositoryImpl.findById(uuid);
        if (existingExperience.isEmpty()) {
            throw new IllegalArgumentException("Experience with uuid " + uuid + " not found.");
        }
        if (Integer.parseInt(experience.getFrom_year()) >= Integer.parseInt(
                experience.getTo_year())) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return experienceRepositoryImpl.save(experience);
    }

    @Transactional
    @Override
    public void deleteExperience(String id) {
        UUID uuid = generateUUID(id);
        experienceRepositoryImpl.deleteById(uuid);
    }

    @Override
    public List<Experience> loadExperienceByNameFilter(String nameFilter) {
        if (nameFilter == null || nameFilter.isEmpty()) {
            return experienceRepositoryImpl.findAll();
        } else {
            return experienceRepositoryImpl.findAllByNameContainingIgnoreCase(nameFilter);
        }
    }

    @Override
    public Experience getExperience(String id) {
        UUID uuid = generateUUID(id);
        return experienceRepositoryImpl.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Experience with uuid " + uuid + " not found."));
    }

    private UUID generateUUID(String id) {
        return UUID.fromString(id);
    }
}