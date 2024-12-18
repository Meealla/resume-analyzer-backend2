package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;

public interface HobbyService {

    Hobby createHobby(Hobby hobby);

    Hobby updateHobby(UUID uuid, Hobby hobby);

    void deleteHobby(UUID uuid);

    List<Hobby> loadHobby(String hobbyFilter);

    Hobby getHobby(UUID uuid);
}