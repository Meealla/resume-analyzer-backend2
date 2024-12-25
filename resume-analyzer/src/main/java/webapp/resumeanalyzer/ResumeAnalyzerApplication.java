package webapp.resumeanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * ResumeAnalyzerApplication.
 */
@SpringBootApplication
@EnableJpaRepositories
public class ResumeAnalyzerApplication {

    /**
     * main метод.
     */
    public static void main(String[] args) {
        SpringApplication.run(ResumeAnalyzerApplication.class, args);
    }

}
