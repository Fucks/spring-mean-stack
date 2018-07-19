package br.projectmanagersoftware.application.util.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 03/11/2016
 */
public class UserNotActivatedException extends AuthenticationException {

    /**
     * 
     * @param msg
     * @param t 
     */
    public UserNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * 
     * @param msg 
     */
    public UserNotActivatedException(String msg) {
        super(msg);
    }
}
