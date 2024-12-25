package webapp.resumeanalyzer.domain.service;

import java.util.List;
import java.util.UUID;
import webapp.resumeanalyzer.domain.model.Resume;

public interface ResumeService {

    Resume createResume(Resume resume);

    Resume updateResume(String id, Resume resume);

    void deleteResume(String id);

    List<Resume> loadResumeByNameFilter(String resumeFilter);

    Resume getResume(String id);
}