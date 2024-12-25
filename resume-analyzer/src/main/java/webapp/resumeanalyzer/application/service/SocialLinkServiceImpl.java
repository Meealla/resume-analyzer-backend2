package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.SocialLink;
import webapp.resumeanalyzer.domain.service.SocialLinkService;
import webapp.resumeanalyzer.infrastructure.repository.SocialLinkRepositoryImpl;

@Service
public class SocialLinkServiceImpl implements SocialLinkService {

    private final SocialLinkRepositoryImpl socialLinkRepositoryImpl;

    public SocialLinkServiceImpl(SocialLinkRepositoryImpl socialLinkRepositoryImpl) {
        this.socialLinkRepositoryImpl = socialLinkRepositoryImpl;
    }

    @Transactional
    @Override
    public SocialLink createSocialLink(SocialLink socialLink) {
        if (socialLink.getName() == null || socialLink.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        return socialLinkRepositoryImpl.save(socialLink);
    }

    @Transactional
    @Override
    public SocialLink updateSocialLink(String id, SocialLink socialLink) {
        UUID uuid = generateUUID(id);
        socialLink.setId(uuid);
        Optional<SocialLink> existingSocialLink = socialLinkRepositoryImpl.findById(uuid);
        if (existingSocialLink.isEmpty()) {
            throw new IllegalArgumentException("SocialLink with uuid " + uuid + " not found.");
        }
        return socialLinkRepositoryImpl.save(socialLink);
    }

    @Transactional
    @Override
    public void deleteSocialLink(String id) {
        UUID uuid = generateUUID(id);
        socialLinkRepositoryImpl.deleteById(uuid);
    }

    @Override
    public List<SocialLink> loadSocialLinkByNameFilter(String socialLinkFilter) {
        if (socialLinkFilter == null || socialLinkFilter.isEmpty()) {
            return socialLinkRepositoryImpl.findAll();
        } else {
            return socialLinkRepositoryImpl.findAllByNameContainingIgnoreCase(socialLinkFilter);
        }
    }

    @Override
    public SocialLink getSocialLink(String id) {
        UUID uuid = generateUUID(id);
        return socialLinkRepositoryImpl.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("SocialLink with uuid " + uuid + " not found."));
    }

    private UUID generateUUID(String id) {
        return UUID.fromString(id);
    }
}
