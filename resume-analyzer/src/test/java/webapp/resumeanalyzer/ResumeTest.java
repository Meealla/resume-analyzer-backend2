package webapp.resumeanalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.model.Experience;
import webapp.resumeanalyzer.domain.model.Hobby;
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.model.SocialLink;

/**
 * Тест сериализации/десериализации JSON для сущности Resume.
 */
@DisplayName("Тест проверки сущности Resume")
public class ResumeTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("сравнение после сериализации и десериализации")
    void testResumeSerializationAndDeserializationEquals() throws JsonProcessingException {
        Resume resume = createSampleResume();
        String jsonString = objectMapper.writeValueAsString(resume);
        Resume deserializedResume = objectMapper.readValue(jsonString, Resume.class);
        assertEquals(resume.getPersonalData(), deserializedResume.getPersonalData());
        assertEquals(resume.getEducations(), deserializedResume.getEducations());
        assertEquals(resume.getExperiences(), deserializedResume.getExperiences());
        assertEquals(resume.getSocialLinks(), deserializedResume.getSocialLinks());
        assertEquals(resume.getHobbies(), deserializedResume.getHobbies());
    }

    @Test
    @DisplayName("сравнение с NULL")
    void testResumeSerializationAndDeserializationNullValues() throws JsonProcessingException {
        Resume resume = new Resume();
        String jsonString = objectMapper.writeValueAsString(resume);
        Resume deserializedResume = objectMapper.readValue(jsonString, Resume.class);
        assertNull(deserializedResume.getId());
        assertNull(deserializedResume.getPersonalData());
        assertNull(deserializedResume.getEducations());
        assertNull(deserializedResume.getExperiences());
        assertNull(deserializedResume.getSocialLinks());
        assertNull(deserializedResume.getHobbies());
    }

    private Resume createSampleResume() {
        return new Resume(UUID.randomUUID(), createPersonalData(), createSetEducation(),
                createSetExperience(), createSocialLink(), createHobby());
    }

    private PersonalData createPersonalData() {
        return new PersonalData(UUID.randomUUID(), "ABC", "A", "B",
                "C", 100L, "X", "Y@Z.ru");
    }

    private Set<Education> createSetEducation() {
        Set<Education> setEducation = new HashSet<>();
        setEducation.add(new Education(UUID.randomUUID(), "X1", "Y1",
                "1971", "2001", "Z1"));
        setEducation.add(new Education(UUID.randomUUID(), "X2", "Y2",
                "1972", "2002", "Z2"));
        setEducation.add(new Education(UUID.randomUUID(), "X3", "Y3",
                "1973", "2003", "Z3"));
        return setEducation;
    }

    private Set<Experience> createSetExperience() {
        Set<Experience> setExperience = new HashSet<>();
        setExperience.add(new Experience(UUID.randomUUID(), "X1", "Y1",
                "1971", "2001", "Z1"));
        setExperience.add(new Experience(UUID.randomUUID(), "X2", "Y2",
                "1972", "2002", "Z2"));
        setExperience.add(new Experience(UUID.randomUUID(), "X3", "Y3",
                "1973", "2003", "Z3"));
        return setExperience;
    }

    private Set<SocialLink> createSocialLink() {
        Set<SocialLink> setSocialLink = new HashSet<>();
        setSocialLink.add(new SocialLink(UUID.randomUUID(), "X1", "Y1"));
        setSocialLink.add(new SocialLink(UUID.randomUUID(), "X2", "Y2"));
        setSocialLink.add(new SocialLink(UUID.randomUUID(), "X3", "Y3"));
        return setSocialLink;
    }

    private Set<Hobby> createHobby() {
        Set<Hobby> setHobby = new HashSet<>();
        setHobby.add(new Hobby(UUID.randomUUID(), "X1"));
        setHobby.add(new Hobby(UUID.randomUUID(), "X2"));
        setHobby.add(new Hobby(UUID.randomUUID(), "X3"));
        return setHobby;
    }
}
