package br.projectmanagersoftware.config.security;

import br.projectmanagersoftware.application.util.exception.UserNotActivatedException;
import br.projectmanagersoftware.entity.account.User;
import br.projectmanagersoftware.repository.account.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 30/10/2015
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private IAccountRepository accountRepository;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //remove todos os '.', '-' e '/' da string para previnir errors.
        User authenticationUser = this.accountRepository.findUserByUsernameAndEnabledTrue(username);
        
         if(authenticationUser != null && !authenticationUser.getEmailActivated()){
            throw new UserNotActivatedException("Usuário não ativo, acesse seu e-mail e confirme seu cadastro.");
        }

        if (authenticationUser == null || authenticationUser.getId() == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return authenticationUser;
    }

}
