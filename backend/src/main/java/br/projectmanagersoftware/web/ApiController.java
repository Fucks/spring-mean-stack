package br.projectmanagersoftware.web;

import br.projectmanagersoftware.entity.account.User;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 23/09/2017
 */
public class ApiController {
    
    public boolean hasPermission(String permission){
        return User.getAuthenticated()
                .getPermissoes()
                .stream()
                .filter((String per)->{ return per.equals(permission); })
                .findAny()
                .isPresent();
    }
}
