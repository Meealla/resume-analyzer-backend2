package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Hobby;
import webapp.resumeanalyzer.domain.service.HobbyService;
import webapp.resumeanalyzer.infrastructure.repository.HobbyRepositoryImpl;

@Service
public class HobbyServiceImpl implements HobbyService {

    private final HobbyRepositoryImpl hobbyRepositoryImpl;

    public HobbyServiceImpl(HobbyRepositoryImpl hobbyRepositoryImpl) {
        this.hobbyRepositoryImpl = hobbyRepositoryImpl;
    }

    @Transactional
    @Override
    public Hobby createHobby(Hobby hobby) {
        if (hobby.getHobby() == null || hobby.getHobby().isEmpty()) {
            throw new IllegalArgumentException("Hobby cannot be null or empty.");
        }
        return hobbyRepositoryImpl.save(hobby);
    }

    @Transactional
    @Override
    public Hobby updateHobby(String id, Hobby hobby) {
        UUID uuid = generateUUID(id);
        hobby.setId(uuid);
        Optional<Hobby> existingHobby = hobbyRepositoryImpl.findById(uuid);
        if (existingHobby.isEmpty()) {
            throw new IllegalArgumentException("Hobby with uuid " + uuid + " not found.");
        }
        return hobbyRepositoryImpl.save(hobby);
    }

    @Transactional
    @Override
    public void deleteHobby(String id) {
        UUID uuid = generateUUID(id);
        hobbyRepositoryImpl.deleteById(uuid);
    }

    @Override
    public List<Hobby> loadHobbyByNameFilter(String hobbyFilter) {
        if (hobbyFilter == null || hobbyFilter.isEmpty()) {
            return hobbyRepositoryImpl.findAll();
        } else {
            return hobbyRepositoryImpl.findAllByHobbyContainingIgnoreCase(hobbyFilter);
        }
    }

    @Override
    public Hobby getHobby(String id) {
        UUID uuid = generateUUID(id);
        return hobbyRepositoryImpl.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Hobby with uuid " + uuid + " not found."));
    }

    private UUID generateUUID(String id) {
        return UUID.fromString(id);
    }
}
