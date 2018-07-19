package br.projectmanagersoftware.service;

import br.projectmanagersoftware.entity.account.Profile;
import br.projectmanagersoftware.repository.account.IProfileRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service criada para manipular perfis de acesso e permissões sem usuário
 * logado e permissão.
 *
 * Utilizar apenas no bootstrap da aplicação.
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 04/11/2016
 */
@Service
public class ProfileService {

    @Autowired
    private IProfileRepository profileRepository;

    
    /**
     * Perfis de acesso
     */
    /**
     *
     * @param profile
     */
    public void deletePerfilDeAcesso(Profile profile) {
        this.profileRepository.delete(profile);
    }
    /**
     *
     * @param profile
     * @return
     */
    public void save(Profile profile) {
        this.profileRepository.save(profile);
    }

    /**
     *
     * @param name
     * @return
     */
    public Profile findProfileByName(String name) {
        return this.profileRepository.findByName(name);
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<Profile> findProfileById(String id) {
        return this.profileRepository.findById(id);
    }

    /**
     *
     * @return
     */
    public List<Profile> listaAllProfiles() {
        return this.profileRepository.findAll();
    }

}
