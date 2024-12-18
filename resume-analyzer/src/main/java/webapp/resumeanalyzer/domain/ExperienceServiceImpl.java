package webapp.resumeanalyzer.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
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
        return experienceRepository.save(experience);
    }

    @Transactional
    @Override
    public Experience updateExperience(UUID uuid, Experience experience) {
        experience.setId(uuid);
        Optional<Experience> existingExperience = experienceRepository.findById(uuid);
        if (existingExperience.isEmpty()) {
            throw new IllegalArgumentException("Experience with uuid " + uuid + " not found.");
        }
        if (Integer.parseInt(experience.getFrom_year()) >= Integer.parseInt(
                experience.getTo_year())) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return experienceRepository.save(experience);
    }

    @Transactional
    @Override
    public void deleteExperience(UUID uuid) {
        experienceRepository.deleteById(uuid);
    }

    @Override
    public List<Experience> loadExperience(String nameFilter) {
        if (nameFilter == null || nameFilter.isEmpty()) {
            return experienceRepository.findAll();
        } else {
            return experienceRepository.findAllByNameContainingIgnoreCase(nameFilter);
        }
    }

    @Override
    public Experience getExperience(UUID uuid) {
        return experienceRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Experience with uuid " + uuid + " not found."));
    }
}