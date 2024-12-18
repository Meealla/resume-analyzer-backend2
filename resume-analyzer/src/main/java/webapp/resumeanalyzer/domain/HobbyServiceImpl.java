package webapp.resumeanalyzer.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class HobbyServiceImpl implements HobbyService {

    private final HobbyRepository hobbyRepository;

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
    public Hobby updateHobby(UUID uuid, Hobby hobby) {
        hobby.setId(uuid);
        Optional<Hobby> existingHobby = hobbyRepository.findById(uuid);
        if (existingHobby.isEmpty()) {
            throw new IllegalArgumentException("Hobby with uuid " + uuid + " not found.");
        }
        return hobbyRepository.save(hobby);
    }

    @Transactional
    @Override
    public void deleteHobby(UUID uuid) {
        hobbyRepository.deleteById(uuid);
    }

    @Override
    public List<Hobby> loadHobby(String hobbyFilter) {
        if (hobbyFilter == null || hobbyFilter.isEmpty()) {
            return hobbyRepository.findAll();
        } else {
            return hobbyRepository.findAllByHobbyContainingIgnoreCase(hobbyFilter);
        }
    }

    @Override
    public Hobby getHobby(UUID uuid) {
        return hobbyRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Hobby with uuid " + uuid + " not found."));
    }
}
