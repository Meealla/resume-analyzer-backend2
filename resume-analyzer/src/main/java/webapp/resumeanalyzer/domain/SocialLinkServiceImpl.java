package webapp.resumeanalyzer.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class SocialLinkServiceImpl implements SocialLinkService {

    private final SocialLinkRepository socialLinkRepository;

    public SocialLinkServiceImpl(SocialLinkRepository socialLinkRepository) {
        this.socialLinkRepository = socialLinkRepository;
    }

    @Transactional
    @Override
    public SocialLink createSocialLink(SocialLink socialLink) {
        if (socialLink.getName() == null || socialLink.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        return socialLinkRepository.save(socialLink);
    }

    @Transactional
    @Override
    public SocialLink updateSocialLink(UUID uuid, SocialLink socialLink) {
        socialLink.setId(uuid);
        Optional<SocialLink> existingSocialLink = socialLinkRepository.findById(uuid);
        if (existingSocialLink.isEmpty()) {
            throw new IllegalArgumentException("SocialLink with uuid " + uuid + " not found.");
        }
        return socialLinkRepository.save(socialLink);
    }

    @Transactional
    @Override
    public void deleteSocialLink(UUID uuid) {
        socialLinkRepository.deleteById(uuid);
    }

    @Override
    public List<SocialLink> loadSocialLink(String socialLinkFilter) {
        if (socialLinkFilter == null || socialLinkFilter.isEmpty()) {
            return socialLinkRepository.findAll();
        } else {
            return socialLinkRepository.findAllByNameContainingIgnoreCase(socialLinkFilter);
        }
    }

    @Override
    public SocialLink getSocialLink(UUID uuid) {
        return socialLinkRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("SocialLink with uuid " + uuid + " not found."));
    }
}
