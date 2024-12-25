package webapp.resumeanalyzer.domain.service;

import java.util.List;
import java.util.UUID;
import webapp.resumeanalyzer.domain.model.Hobby;

public interface HobbyService {

    Hobby createHobby(Hobby hobby);

    Hobby updateHobby(String id, Hobby hobby);

    void deleteHobby(String id);

    List<Hobby> loadHobbyByNameFilter(String hobbyFilter);

    Hobby getHobby(String id);
}