package br.projectmanagersoftware.web.exceptions;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 04/11/2015
 */
@ControllerAdvice("br.projectmanagersoftware.web.controller")
public class ExceptionHandlingController {

    @Autowired
    @Qualifier("exceptionMessageSource")
    private MessageSource exceptionMessageSource;

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param req
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ResponseEntity handleError(HttpServletRequest req, Exception exception) {
        LOG.info("Exception detected!!! ->" + exception.getMessage());
        return new ResponseEntity(exception.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
