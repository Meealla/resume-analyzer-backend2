package webapp.deepseek.domain.service;

public interface DeepSeekService {
    String analyzeResume(String resumeText, String jobDescription);

    public boolean isValidResumeOrJobDescription(String text);
}
