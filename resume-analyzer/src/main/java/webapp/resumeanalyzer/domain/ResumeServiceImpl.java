package webapp.resumeanalyzer.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Transactional
    @Override
    public Resume createResume(Resume resume) {
        if (resume.getPersonalData().getFull_name() == null || resume.getPersonalData()
                .getFull_name().isEmpty()) {
            throw new IllegalArgumentException(
                    "Full_name or PersonalData cannot be null or empty.");
        }
        return resumeRepository.save(resume);
    }

    @Transactional
    @Override
    public Resume updateResume(UUID uuid, Resume resume) {
        resume.setId(uuid);
        Optional<Resume> existingResume = resumeRepository.findById(uuid);
        if (existingResume.isEmpty()) {
            throw new IllegalArgumentException("Resume with uuid " + uuid + " not found.");
        }
        return resumeRepository.save(resume);
    }

    @Transactional
    @Override
    public void deleteResume(UUID uuid) {
        resumeRepository.deleteById(uuid);
    }

    @Override
    public List<Resume> loadResume(String resumeFilter) {
        if (resumeFilter == null || resumeFilter.isEmpty()) {
            return resumeRepository.findAll();
        } else {
            return resumeRepository.findByKeywordIgnoreCase(resumeFilter);
        }
    }

    @Override
    public Resume getResume(UUID uuid) {
        return resumeRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Resume with uuid " + uuid + " not found."));
    }
}