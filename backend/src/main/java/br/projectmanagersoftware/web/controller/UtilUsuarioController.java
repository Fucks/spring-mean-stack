package br.projectmanagersoftware.web.controller;

import br.projectmanagersoftware.config.ApplicationConfig;
import br.projectmanagersoftware.entity.account.User;
import br.projectmanagersoftware.service.AccountService;
import com.adobe.xmp.impl.Base64;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 04/11/2016
 */
@Controller
@RequestMapping
public class UtilUsuarioController {

    @Autowired
    private AccountService accountService;

    /**
     * Endpoint para ativar conta de usuário
     */
    @RequestMapping(path = "activate/{idencoded}", method = RequestMethod.GET)
    public void sendRequestResetPassword(@PathVariable(value = "idencoded") String idencoded, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String userId = String.valueOf(Base64.decode(idencoded));

        //ativa o usuário
        this.accountService.activateRegister(userId);

        //envia para o login
        response.sendRedirect("/#/login?first-time=true");
    }

    /**
     * Endpoint para redefinir a senha do usuário, caso ele tenha esquecido a
     * senha.
     *
     * @param params
     */
    @RequestMapping(path = "api/mudar-senha", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void sendRequestResetPassword(@RequestBody Map<String, String> params) throws NoSuchAlgorithmException {
        final String username = params.get("username").replaceAll("\\.|\\-|\\/", "");
        final String email = params.get("email");

        this.accountService.requestResetPassword(username, email);
    }

    /**
     * Endpoint de validação e redirecionamento das informações enviadas pelo
     * formulário do e-mail de alteração de senha.
     *
     * @param username
     * @param token
     */
    @RequestMapping(path = "api/mudar-senha", method = RequestMethod.GET)
    public String sendResetPassword(String username, String token, HttpServletRequest req) {
        final User user = this.accountService.findUserByUsername(username);

        //verifica se existe um usuário
        if (user == null) {
            throw new IllegalStateException("Impossível obter as informações do usuário.");
        }

        //verifica se o usuário possui um token de alteração de senha
        if (user.getTokenResetPassword() == null || user.getTokenResetPassword().isEmpty()) {
            throw new IllegalStateException("O usuário não possui um pedido de alteração de senha.");
        }

        //verifica se o token informado é o mesmo token do usuário
        if (!user.getTokenResetPassword().equals(token)) {
            throw new IllegalStateException("Token inválido.");
        }

        //verifica se o token existe e se a data é menor que 24h 
        final Calendar tokenDate = Calendar.getInstance();
        tokenDate.setTime(user.getTokenResetPasswordDate());
        tokenDate.add(Calendar.HOUR, 24);

        //se a data atual é menor q 24h após a criação do token, e se o token é realmente igual
        if (Calendar.getInstance().before(tokenDate) && token.equals(user.getTokenResetPassword())) {
            return "/change-password";
        }
        return "/";
    }

    /**
     * Endpoint para resetar a senha do usuário
     *
     * @param username
     * @param token
     * @param password
     * @param confirmaPassword
     */
    @RequestMapping(path = "api/mudar-senha/reset", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity resetPassword(String username, String token, String password, String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Senha e confirmar senha não conferem.");
        }

        final User user = this.accountService.findUserByUsername(username);

        //verifica se o token existe e se a data é menor que 24h 
        final Calendar tokenDate = Calendar.getInstance();
        tokenDate.setTime(user.getTokenResetPasswordDate());
        tokenDate.add(Calendar.HOUR, 24);

        //se a data atual é menor q 24h após a criação do token, e se o token é realmente igual
        if (Calendar.getInstance().before(tokenDate) && token.equals(user.getTokenResetPassword())) {
            this.accountService.resetPassword(password, user);

            return ResponseEntity.ok( "Senha alterada com sucesso!");
        }

        throw new IllegalArgumentException("Não foi possível alterar sua senha, tente novamente mais tarde ou faça uma nova requisição.");
    }

}
