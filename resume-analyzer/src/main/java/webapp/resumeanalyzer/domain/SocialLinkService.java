package webapp.resumeanalyzer.domain;

import java.util.List;
import java.util.UUID;

public interface SocialLinkService {

    SocialLink createSocialLink(SocialLink socialLink);

    SocialLink updateSocialLink(UUID uuid, SocialLink socialLink);

    void deleteSocialLink(UUID uuid);

    List<SocialLink> loadSocialLink(String nameFilter);

    SocialLink getSocialLink(UUID uuid);
}
