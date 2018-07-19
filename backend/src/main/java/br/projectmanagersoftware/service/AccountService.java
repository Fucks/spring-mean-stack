package br.projectmanagersoftware.service;

import br.projectmanagersoftware.application.SystemPermissions;
import br.projectmanagersoftware.application.infra.mail.MailService;
import br.projectmanagersoftware.entity.account.Profile;
import br.projectmanagersoftware.entity.account.User;
import br.projectmanagersoftware.repository.account.IAccountRepository;
import br.projectmanagersoftware.repository.account.IProfileRepository;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 29/10/2015
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IProfileRepository profileRepository;

    @Autowired
    @Lazy
    private MailService mailService;

    /**
     * Users
     */
    /**
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.USUARIOS_INSERT + "')")
    public User save(User user) {
        user.setEnabled(Boolean.TRUE);
        user.setEmailActivated(Boolean.TRUE);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String password = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        return this.accountRepository.save(user);
    }

    /**
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.USUARIOS_UPDATE + "')")
    public User update(User user) {
        final User dbUser = this.findById(user.getId()).get();

        if (!dbUser.getPassword().equals(user.getPassword())) {
            String password = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }

        return this.accountRepository.save(user);
    }

    /**
     *
     * @param user
     * @return
     */
    public User saveDefault(User user) {
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setEnabled(Boolean.TRUE);

        return this.accountRepository.save(user);
    }

    /**
     *
     * @param user
     * @return
     */
    public User registerNewUser(User user) {
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setEnabled(Boolean.TRUE);

        user.setProfile(this.findProfileByName("Fornecedor"));

        return this.accountRepository.save(user);
    }

    /**
     *
     * @param filter
     * @param page
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.USUARIOS_LIST + "')")
    @Transactional(readOnly = true)
    public Page<User> listByParams(String filter, Pageable page) {
        return this.accountRepository.findAll(page);
    }

    /**
     *
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.USUARIOS_LIST + "')")
    @Transactional(readOnly = true)
    public Iterable<User> listAll() {
        return this.accountRepository.findAll();
    }

    /**
     *
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return this.accountRepository.findUserByUsername(username);
    }

    /**
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.USUARIOS_LIST + "')")
    @Transactional(readOnly = true)
    public Optional<User> findById(String id) {
        return this.accountRepository.findById(id);
    }

    /**
     *
     * @param users
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.USUARIOS_REMOVE + "')")
    public void delete(List<User> users) {
        for (User user : users) {
            this.accountRepository.delete(user);
        }
    }

    /**
     *
     * @param user
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.USUARIOS_REMOVE + "')")
    public void delete(User user) {
        this.accountRepository.delete(user);
    }

    /**
     *
     * @return
     */
    public Long countUsers() {
        return this.accountRepository.count();
    }

    /**
     *
     * @param profile
     */
    public void deletePerfilDeAcesso(Profile profile) {
        this.profileRepository.delete(profile);
    }

    /**
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasPermission(#principal, '" + SystemPermissions.USUARIOS_UPDATE + "')")
    public User enableOrDisableUser(User user) {
        user.setEnabled(!user.isEnabled());

        return this.accountRepository.save(user);
    }

    /**
     * Ativa o registro do usuário.
     *
     * @param userId
     */
    public void activateRegister(String userId) {
        final User account = this.accountRepository.findById(userId).get();

        if (!account.getEmailActivated()) {
            account.setEmailActivated(Boolean.TRUE);

            this.accountRepository.save(account);
        }
    }

    /**
     * Perfis de acesso
     */
    /**
     *
     * @param profile
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.PROFILE_INSERT + "')")
    public void save(Profile profile) {
        this.profileRepository.save(profile);
    }

    /**
     *
     * @param name
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.PROFILE_LIST + "')")
    public Profile findProfileByName(String name) {
        return this.profileRepository.findByName(name);
    }

    /**
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.PROFILE_LIST + "')")
    public Optional<Profile> findProfileById(String id) {
        return this.profileRepository.findById(id);
    }

    /**
     *
     * @return
     */
    @PreAuthorize("hasPermission(#principal,'" + SystemPermissions.PROFILE_LIST + "')")
    public List<Profile> listaAllProfiles() {
        return this.profileRepository.findAll();
    }

    /**
     *
     * @return
     */
    public Long countProfiles() {
        return this.profileRepository.count();
    }

    /**
     *
     * @param password
     * @param newPassword
     */
    public void changePassword(String password, String newPassword) {
        User currentUser = User.getAuthenticated();

        currentUser = this.findById(currentUser.getId()).get();

        if (this.passwordEncoder.matches(password, currentUser.getPassword())) {
            currentUser.setPassword(this.passwordEncoder.encode(newPassword));
            this.accountRepository.save(currentUser);
        } else {
            throw new IllegalArgumentException("Senha informada não confere com a senha atual.");
        }
    }

    /**
     * Reseta a senha do usuário, para isso envia um e-mail com um token e um
     * link de redefinição de senha.
     *
     * @param username
     * @param email
     */
    public void requestResetPassword(String username, String email) throws NoSuchAlgorithmException {
        final User user = this.accountRepository.findByUsernameAndEmail(username, email);

        /**
         * Caso exista um usuário com o username e email informados, então envia
         * e-mail e cria o token
         */
        if (user != null) {
            final String token = UUID.randomUUID().toString();

            final Map<String, Object> model = new HashMap<String, Object>();

            model.put("email", user.getEmail());
            model.put("token", token);
            model.put("username", user.getUsername());
            model.put("nome", user.getFullName());

            user.setTokenResetPassword(token);
            user.setTokenResetPasswordDate(Calendar.getInstance().getTime());

            //atualiza o usuário com informações de token
            this.accountRepository.save(user);

            this.mailService.resetPasswordMailSend(model);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
    }

    /**
     * Reseta a senha pelo portal externo
     *
     * @param password
     * @param userId
     */
    public void resetPassword(String password, User user) {
        user.setTokenResetPassword(null);
        user.setTokenResetPasswordDate(null);
        user.setPassword(this.passwordEncoder.encode(password));

        this.accountRepository.save(user);
    }
}
