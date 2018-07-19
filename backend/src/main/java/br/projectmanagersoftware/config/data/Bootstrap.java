package br.projectmanagersoftware.config.data;

import br.projectmanagersoftware.entity.account.Profile;
import br.projectmanagersoftware.entity.account.User;
import br.projectmanagersoftware.service.AccountService;
import br.projectmanagersoftware.service.ProfileService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 17/11/2015
 */
public class Bootstrap implements InitializingBean {
    
    private final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ProfileService profileService;
    
    /**
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.createProfiles();
        this.createUsers();
    }

    /**
     *
     */
    public void createProfiles() {
        LOG.info("====================# Bootstraping perfis de acesso #====================");
        
        Profile admProfile = this.profileService.findProfileByName("Administrador");
        
        if (admProfile == null) {
            admProfile = new Profile();
            admProfile.setName("Administrador");
            admProfile.setPermissoes(new ArrayList<>());
        }
        
        admProfile.setPermissoes(br.projectmanagersoftware.application.SystemPermissions.getAllPermissions());
        
        this.profileService.save(admProfile);

        //Profile default user.
        Profile userProfile = this.profileService.findProfileByName("Fornecedor");
        
        if (userProfile == null) {
            userProfile = new Profile();
            userProfile.setName("Fornecedor");
            userProfile.setPermissoes(new ArrayList<>());
        }
        
        this.profileService.save(userProfile);
        
        LOG.info("====================# Perfis de acesso criados #====================");
    }

    /**
     *
     */
    public void createUsers() {
        LOG.info("====================# Bootstraping usuários #====================");
        
        if (this.accountService.countUsers() > 0) {
            return;
        }
        
        final User user = new User();
        
        user.setFullName("Administrador do sistema");
        user.setEmail("adm@email.com");
        user.setProfile(this.profileService.findProfileByName("Administrador"));
        user.setPassword("admin");
        user.setUsername("admin");
        user.setEmailActivated(Boolean.TRUE);
        
        this.accountService.saveDefault(user);
        
        LOG.info("====================# usuários criados #====================");
        
    }
}
