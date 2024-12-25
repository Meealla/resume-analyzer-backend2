package webapp.resumeanalyzer.domain.service;

import java.util.List;
import java.util.UUID;
import webapp.resumeanalyzer.domain.model.SocialLink;

public interface SocialLinkService {

    SocialLink createSocialLink(SocialLink socialLink);

    SocialLink updateSocialLink(String id, SocialLink socialLink);

    void deleteSocialLink(String id);

    List<SocialLink> loadSocialLinkByNameFilter(String nameFilter);

    SocialLink getSocialLink(String id);
}
