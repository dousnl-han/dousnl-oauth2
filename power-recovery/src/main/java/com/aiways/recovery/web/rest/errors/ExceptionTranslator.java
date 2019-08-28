package com.aiways.recovery.web.rest.errors;

import com.connext.common.exception.ServiceException;
import com.connext.common.web.rest.errors.AbstractExceptionTranslator;
import com.connext.common.web.rest.errors.ErrorVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class ExceptionTranslator extends AbstractExceptionTranslator {
    private final MessageSource messageSource;
    private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);
    protected ExceptionTranslator(MessageSource messageSource) {
        super(messageSource);
        this.messageSource = messageSource;
    }

    protected String localTranslate(Locale language, ServiceException ex, String... params) {
        try {
            return this.messageSource.getMessage(ex.getErrorCode(), params, language);
        } catch (NoSuchMessageException var5) {
            return ex.getErrorMessage();
        }
    }

    @Override
    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processServiceError(HttpServletRequest request, ServiceException ex) {
        ErrorVM error = new ErrorVM(ex.getErrorCode(), this.localTranslate(request.getLocale(), ex));
        this.log.error("ServiceException[" + error.getErrorId() + "]: " + ex.getMessage(), ex);
        return error;
    }
}
