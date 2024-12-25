package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.service.ResumeService;
import webapp.resumeanalyzer.infrastructure.repository.ResumeRepositoryImpl;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepositoryImpl resumeRepositoryImpl;

    public ResumeServiceImpl(ResumeRepositoryImpl resumeRepositoryImpl) {
        this.resumeRepositoryImpl = resumeRepositoryImpl;
    }

    @Transactional
    @Override
    public Resume createResume(Resume resume) {
        if (resume.getPersonalData().getFull_name() == null || resume.getPersonalData()
                .getFull_name().isEmpty()) {
            throw new IllegalArgumentException(
                    "Full_name or PersonalData cannot be null or empty.");
        }
        return resumeRepositoryImpl.save(resume);
    }

    @Transactional
    @Override
    public Resume updateResume(String id, Resume resume) {
        UUID uuid = generateUUID(id);
        resume.setId(uuid);
        Optional<Resume> existingResume = resumeRepositoryImpl.findById(uuid);
        if (existingResume.isEmpty()) {
            throw new IllegalArgumentException("Resume with uuid " + uuid + " not found.");
        }
        return resumeRepositoryImpl.save(resume);
    }

    @Transactional
    @Override
    public void deleteResume(String id) {
        UUID uuid = generateUUID(id);
        resumeRepositoryImpl.deleteById(uuid);
    }

    @Override
    public List<Resume> loadResumeByNameFilter(String resumeFilter) {
        if (resumeFilter == null || resumeFilter.isEmpty()) {
            return resumeRepositoryImpl.findAll();
        } else {
            return resumeRepositoryImpl.findByKeywordIgnoreCase(resumeFilter);
        }
    }

    @Override
    public Resume getResume(String id) {
        UUID uuid = generateUUID(id);
        return resumeRepositoryImpl.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Resume with uuid " + uuid + " not found."));
    }

    private UUID generateUUID(String id) {
        return UUID.fromString(id);
    }
}