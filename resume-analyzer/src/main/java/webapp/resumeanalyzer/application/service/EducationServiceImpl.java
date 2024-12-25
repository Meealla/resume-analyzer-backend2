package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.service.EducationService;
import webapp.resumeanalyzer.infrastructure.repository.EducationRepositoryImpl;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepositoryImpl educationRepositoryImpl;

    @Autowired
    public EducationServiceImpl(EducationRepositoryImpl educationRepositoryImpl) {
        this.educationRepositoryImpl = educationRepositoryImpl;
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
        return educationRepositoryImpl.save(education);
    }

    @Transactional
    @Override
    public Education updateEducation(String id, Education education) {
        UUID uuid = generateUUID(id);
        education.setId(uuid);
        Optional<Education> existingEducation = educationRepositoryImpl.findById(uuid);
        if (existingEducation.isEmpty()) {
            throw new IllegalArgumentException("Education with uuid " + uuid + " not found.");
        }
        if (Integer.parseInt(education.getFrom_year()) >= Integer.parseInt(
                education.getTo_year())) {
            throw new IllegalArgumentException("FromYear cannot be more than ToYear.");
        }
        return educationRepositoryImpl.save(education);
    }

    @Transactional
    @Override
    public void deleteEducation(String id) {
        UUID uuid = generateUUID(id);
        educationRepositoryImpl.deleteById(uuid);
    }

    @Override
    public List<Education> loadEducationByNameFilter(String nameFilter) {
        if (nameFilter == null || nameFilter.isEmpty()) {
            return educationRepositoryImpl.findAll();
        } else {
            return educationRepositoryImpl.findAllByNameContainingIgnoreCase(nameFilter);
        }
    }

    @Override
    public Education getEducation(String id) {
        UUID uuid = generateUUID(id);
        return educationRepositoryImpl.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Education with uuid " + uuid + " not found."));
    }

    private UUID generateUUID(String id) {
        return UUID.fromString(id);
    }
}