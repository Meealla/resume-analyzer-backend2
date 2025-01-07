package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Hobby;
import webapp.resumeanalyzer.domain.repository.HobbyRepository;
import webapp.resumeanalyzer.domain.service.HobbyService;


/**
 * Сервис CRUD методов сущности Hobby.
 */
@Service
public class HobbyServiceImpl implements HobbyService {

    private final HobbyRepository hobbyRepository;

    /**
     * Метод для внедрения зависимостей.
     */
    @Autowired
    public HobbyServiceImpl(HobbyRepository hobbyRepository) {
        this.hobbyRepository = hobbyRepository;
    }

    @Transactional
    @Override
    public Hobby createHobby(Hobby hobby) {
        if (hobby.getHobby() == null || hobby.getHobby().isEmpty()) {
            throw new IllegalArgumentException("Hobby cannot be null or empty.");
        }
        return hobbyRepository.save(hobby);
    }

    @Transactional
    @Override
    public Hobby updateHobby(String id, Hobby hobby) {
        UUID uuid = generateUuid(id);
        hobby.setId(uuid);
        Optional<Hobby> existingHobby = hobbyRepository.findById(uuid);
        if (existingHobby.isEmpty()) {
            throw new IllegalArgumentException("Hobby with uuid " + uuid + " not found.");
        }
        return hobbyRepository.save(hobby);
    }

    @Transactional
    @Override
    public void deleteHobby(String id) {
        UUID uuid = generateUuid(id);
        hobbyRepository.deleteById(uuid);
    }

    @Override
    public List<Hobby> loadHobbyByNameFilter(String hobbyFilter) {
        if (hobbyFilter == null || hobbyFilter.isEmpty()) {
            return hobbyRepository.findAll();
        } else {
            return hobbyRepository.findAllByHobbyContainingIgnoreCase(hobbyFilter);
        }
    }

    @Override
    public Hobby getHobbyById(String id) {
        UUID uuid = generateUuid(id);
        return hobbyRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Hobby with uuid " + uuid + " not found."));
    }

    private UUID generateUuid(String id) {
        return UUID.fromString(id);
    }
}
