package webapp.resumeanalyzer;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import webapp.resumeanalyzer.application.service.ResumeServiceImpl;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.repository.ResumeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ResumeServiceTest {

    @Mock
    private ResumeRepository resumeRepository;

    @InjectMocks
    private ResumeServiceImpl resumeService;

    @Test
    public void getResumeNonEmpty() {

        String query = "developer";
        Pageable pageable = PageRequest.of(0, 10);
        Resume resume = new Resume();
        Page<Resume> page = new PageImpl<>(List.of(resume));

        when(resumeRepository.searchResumes(anyString(), any(Pageable.class))).thenReturn(page);

        Page<Resume> resumes = resumeService.searchResumes(query, pageable);

        assertThat(resumes).isNotNull();
        assertThat(resumes.getContent()).hasSize(1);
    }

    @Test
    public void getResumeEmpty() {

        String query = "";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Resume> resumes = resumeService.searchResumes(query, pageable);

        assertThat(resumes).isNotNull();
        assertThat(resumes.getContent()).isEmpty();

    }


}
