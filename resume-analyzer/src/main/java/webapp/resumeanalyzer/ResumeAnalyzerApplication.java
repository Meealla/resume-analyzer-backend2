package webapp.resumeanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ResumeAnalyzerApplication.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ResumeAnalyzerApplication {

    /**
     * main метод.
     */
    public static void main(String[] args) {
        SpringApplication.run(ResumeAnalyzerApplication.class, args);
    }

}
