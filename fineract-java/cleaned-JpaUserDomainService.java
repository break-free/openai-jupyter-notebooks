
package org.apache.fineract.useradministration.domain;
import org.apache.fineract.infrastructure.core.service.PlatformEmailService;
import org.apache.fineract.infrastructure.security.service.PlatformPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class JpaUserDomainService implements UserDomainService {
    private final AppUserRepository userRepository;
    private final PlatformPasswordEncoder applicationPasswordEncoder;
    private final PlatformEmailService emailService;
    @Autowired
    public JpaUserDomainService(final AppUserRepository userRepository, final PlatformPasswordEncoder applicationPasswordEncoder,
            final PlatformEmailService emailService) {
        this.userRepository = userRepository;
        this.applicationPasswordEncoder = applicationPasswordEncoder;
        this.emailService = emailService;
    }
    @Transactional
    @Override
    public void create(final AppUser appUser, final Boolean sendPasswordToEmail) {
        generateKeyUsedForPasswordSalting(appUser);
        final String unencodedPassword = appUser.getPassword();
        final String encodePassword = this.applicationPasswordEncoder.encode(appUser);
        appUser.updatePassword(encodePassword);
        this.userRepository.saveAndFlush(appUser);
        if (sendPasswordToEmail.booleanValue()) {
            this.emailService.sendToUserAccount(appUser.getOffice().getName(), appUser.getFirstname(), appUser.getEmail(),
                    appUser.getUsername(), unencodedPassword);
        }
    }
    private void generateKeyUsedForPasswordSalting(final AppUser appUser) {
        this.userRepository.save(appUser);
    }
}
