package webapp.resumeanalyzer.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Transactional
    @Override
    public Education createEducation(Education education) {
        if (education.getName() == null || education.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (Integer.parseInt(education.getFrom_year()) >= Integer.parseInt(
                education.getTo_year())) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return educationRepository.save(education);
    }

    @Transactional
    @Override
    public Education updateEducation(UUID uuid, Education education) {
        education.setId(uuid);
        Optional<Education> existingEducation = educationRepository.findById(uuid);
        if (existingEducation.isEmpty()) {
            throw new IllegalArgumentException("Education with uuid " + uuid + " not found.");
        }
        if (Integer.parseInt(education.getFrom_year()) >= Integer.parseInt(
                education.getTo_year())) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return educationRepository.save(education);
    }

    @Transactional
    @Override
    public void deleteEducation(UUID uuid) {
        educationRepository.deleteById(uuid);
    }

    @Override
    public List<Education> loadEducation(String nameFilter) {
        if (nameFilter == null || nameFilter.isEmpty()) {
            return educationRepository.findAll();
        } else {
            return educationRepository.findAllByNameContainingIgnoreCase(nameFilter);
        }
    }

    @Override
    public Education getEducation(UUID uuid) {
        return educationRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Education with uuid " + uuid + " not found."));
    }
}