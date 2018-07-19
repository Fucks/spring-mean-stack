package br.projectmanagersoftware.web.controller;

import br.projectmanagersoftware.application.SystemPermissions;
import br.projectmanagersoftware.entity.account.Profile;
import br.projectmanagersoftware.entity.account.User;
import br.projectmanagersoftware.service.AccountService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 30/10/2015
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AccountService accountService;

    
    /**
     * Get users paginated
     * 
     * @param filter
     * @param page
     * @return 
     */
    @GetMapping("")
    public ResponseEntity listUsers(String filter, Pageable page){
        return ResponseEntity.ok(
            this.accountService.listByParams(filter, page));
    }
    
    /**
     *
     * @param user
     * @throws Exception
     */
    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) throws Exception {
        user.setEnabled(Boolean.TRUE);
        user.setEmailActivated(Boolean.TRUE);
        this.accountService.save(user);

        return ResponseEntity.ok("Usuário cadastrado!");
    }

    /**
     *
     * @param user
     * @throws Exception
     */
    @RequestMapping(value = "/admin/enable-disable", method = RequestMethod.POST)
    public ResponseEntity enableOrDisableUser(@RequestBody User user) throws Exception {
        user = this.accountService.enableOrDisableUser(user);

        return ResponseEntity.ok(user.isEnabled() ? "Usuário ativado!" : "Usuário desativado!");
    }

    /**
     *
     * @param user
     * @throws Exception
     */
    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user) throws Exception {
        this.accountService.update(user);

        return ResponseEntity.ok("Usuário atualizado!");
    }

    /**
     *
     * @param user
     */
    @RequestMapping(value = "/admin/delete", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity remove(@RequestBody User user) {
        this.accountService.delete(user);

        return ResponseEntity.ok("Usuário removido!");
    }

    /**
     *
     * @param filter
     * @param page
     * @return
     */
    @RequestMapping(value = "/admin/list", method = RequestMethod.GET)
    public ResponseEntity listUsersByParams() {
        return ResponseEntity.ok(this.accountService.listAll());
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin/find", method = RequestMethod.GET)
    public ResponseEntity findUser(@RequestParam("id") String id) {
        return ResponseEntity.ok(this.accountService.findById(id));
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity getAuthenticatedUser() {
        final User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User currentUser = new User();

        currentUser.setFullName(loggedUser.getFullName());
        currentUser.setEmail(loggedUser.getEmail());
        currentUser.setUsername(loggedUser.getUsername());
        currentUser.setEnabled(loggedUser.isEnabled());

        return ResponseEntity.ok(currentUser);
    }

    /*
    * PROFILES
     */
    /**
     *
     * @return
     */
    @RequestMapping(value = "/admin/profiles", method = RequestMethod.GET)
    public ResponseEntity getProfiles() {
        return ResponseEntity.ok(this.accountService.listaAllProfiles());
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/admin/perfil-de-acesso/find", method = RequestMethod.GET)
    public ResponseEntity findProfileById(@RequestParam String id) {
        return ResponseEntity.ok(this.accountService.findProfileById(id));
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/admin/perfil-de-acesso/update", method = RequestMethod.POST)
    public ResponseEntity updateProfile(@RequestBody Profile profile) {
        this.accountService.save(profile);

        return ResponseEntity.ok( profile.getId() == null ? "Perfil de acesso criado" : "Perfil de acesso atualizado!");
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/admin/perfil-de-acesso/remove", method = RequestMethod.DELETE)
    public ResponseEntity removeProfile(@RequestBody Profile profile) {
        this.accountService.deletePerfilDeAcesso(profile);

        return ResponseEntity.ok("Perfil de acesso removido!");
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/admin/all-permissions-grouped", method = RequestMethod.GET)
    public ResponseEntity getAllPermissions() {
        return ResponseEntity.ok(SystemPermissions.getAllPermissions());
    }

    /**
     * Endpoint para alterar a senha de usuários. Para altera-la somente a
     * pessoa logada.
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody Map<String, String> params) {
        final String password = params.get("password");
        final String novoPassword = params.get("novoPassword");

        this.accountService.changePassword(password, novoPassword);

        return ResponseEntity.ok("Senha alterada!");
    }
}
