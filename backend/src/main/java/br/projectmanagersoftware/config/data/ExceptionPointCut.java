/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projectmanagersoftware.config.data;

import java.util.Locale;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fucks
 */
@Component
@Order(5)
@Aspect
public class ExceptionPointCut {

    private final Logger LOG = Logger.getLogger(ExceptionPointCut.class);

    //SQLState to duplicated data field.
    public final String DUPLICATED_REGISTER_ERROR = "23505";
    public final String FOREING_KEY_REFERENCED_ERROR = "23503";

    //error messages
    public final String ACCESS_DENIED_EXCEPTION_MESSAGE = "access-denied.error.message";

    @Autowired
    @Qualifier("fieldsMessageSource")
    private MessageSource fieldsMessageSource;

    /**
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* br.projectmanagersoftware.service.*.insert*(..)) || "
            + "execution(* br.projectmanagersoftware.service.*.update*(..)) || "
            + "execution(* br.projectmanagersoftware.service.*.save*(..)) || "
            + "execution(* br.projectmanagersoftware.service.*.remove*(..)) || "
            + "execution(* br.projectmanagersoftware.service.*.delete*(..))")
    public Object doHandleDataIntegrityViolationException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DataIntegrityViolationException exception) {
            LOG.info("Exception detected!!! ->" + exception.getMessage());

            throw exception;
        } catch (AccessDeniedException exception) {
            LOG.info("Exception detected!!! ->" + exception.getMessage());
            throw new AccessDeniedException(fieldsMessageSource.getMessage(ACCESS_DENIED_EXCEPTION_MESSAGE, null, Locale.getDefault()));
        } catch (Exception exception) {
            LOG.info("Exception detected!!! ->" + exception.getMessage());
            throw exception;
        }
    }
}
