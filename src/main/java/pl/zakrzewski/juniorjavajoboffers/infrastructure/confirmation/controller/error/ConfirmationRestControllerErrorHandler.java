package pl.zakrzewski.juniorjavajoboffers.infrastructure.confirmation.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenAlreadyConfirmed;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenHasExpired;
import pl.zakrzewski.juniorjavajoboffers.infrastructure.register.controller.error.ErrorResponse;

@ControllerAdvice
@Log4j2
public class ConfirmationRestControllerErrorHandler {

    @ExceptionHandler(TokenAlreadyConfirmed.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleTokenAlreadyConfirmed(TokenAlreadyConfirmed exception) {
        String message = exception.getMessage();
        log.error(message);
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenHasExpired.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleTokenHasExpired(TokenHasExpired exception) {
        String message = exception.getMessage();
        log.error(message);
        return new ErrorResponse(message, HttpStatus.UNAUTHORIZED);
    }
}
