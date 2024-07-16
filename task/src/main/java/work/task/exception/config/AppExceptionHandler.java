package work.task.exception.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import work.task.exception.UnauthorizedException;

/**
 * Global exception handler for the application.
 * <p>
 * This class handles exceptions thrown across the whole application and provides appropriate responses.
 * </p>
 */
@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    /**
     * Handles UsernameNotFoundException and returns a structured error response.
     *
     * @param exception the UsernameNotFoundException thrown
     * @return {@link ErrorStruct}, {@link HttpStatus#NOT_FOUND}
     */
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        var errorStruct = ErrorStruct.withDefaultLocalDateTime(HttpStatus.NOT_FOUND, exception.getMessage()).build();
        return new ResponseEntity<>(errorStruct, errorStruct.getHttpStatus());
    }

    /**
     * Handles DataIntegrityException and returns a structured error response.
     *
     * @param exception the DataIntegrityViolationException thrown
     * @return {@link ErrorStruct}, {@link HttpStatus#CONFLICT}
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityException( DataIntegrityViolationException exception){
        log.error(exception.getMessage(),exception);
        var errorStruct = ErrorStruct.withDefaultLocalDateTime(HttpStatus.CONFLICT,exception.getMessage()).build();
        return new ResponseEntity<>(errorStruct,errorStruct.getHttpStatus());
    }

    /**
     * Handles UnauthorizedException and returns a structured error response.
     *
     * @param exception the UnauthorizedException thrown
     * @return {@link ErrorStruct}, {@link HttpStatus#UNAUTHORIZED}
     *
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException exception){
        log.error(exception.getMessage(),exception);
        var errorStruct = ErrorStruct.withDefaultLocalDateTime(HttpStatus.UNAUTHORIZED,exception.getMessage()).build();
        return new ResponseEntity<>(errorStruct,errorStruct.getHttpStatus());
    }

    /**
     * Handles AccessDeniedException and returns a structured error response.
     *
     * @param exception the AccessDeniedException thrown
     * @return {@link ErrorStruct}, {@link HttpStatus#FORBIDDEN}
     *
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){
        log.error(exception.getMessage(),exception);
        var errorStruct = ErrorStruct.withDefaultLocalDateTime(HttpStatus.FORBIDDEN,exception.getMessage()).build();
        return new ResponseEntity<>(errorStruct,errorStruct.getHttpStatus());
    }


}
