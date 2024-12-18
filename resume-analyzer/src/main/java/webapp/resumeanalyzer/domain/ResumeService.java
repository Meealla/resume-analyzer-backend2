package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.config.Task;

public interface ResumeService {

    Resume createResume(Resume resume);

    Resume updateResume(UUID uuid, Resume resume);

    void deleteResume(UUID uuid);

    List<Resume> loadResume(String resumeFilter);

    Resume getResume(UUID uuid);
}