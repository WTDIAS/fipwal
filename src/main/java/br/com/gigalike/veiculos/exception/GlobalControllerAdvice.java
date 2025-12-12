package br.com.gigalike.veiculos.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(ExceptionInternalServerError.class)
    public ResponseEntity<ResponseError>exceptionInternalServerError(ExceptionInternalServerError ex){
        ResponseError responseErrror = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now());
        logger.warn("ExceptionInternalServerError: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseErrror);
    }

    @ExceptionHandler(ExceptionBadRequest.class)
    public ResponseEntity<ResponseError> exceptionBadRequest(ExceptionBadRequest ex){
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
        logger.error(this.getClass().getName()+": ",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(ExceptionNotFound.class)
    public ResponseEntity<ResponseError> exceptionNotFound(ExceptionNotFound ex){
        ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND,ex.getMessage(),LocalDateTime.now());
        logger.error(this.getClass().getName()+" : ",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> globalException(Exception ex){
        ResponseError responseErrror = new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now());
        logger.error("Global Exception: ",ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseErrror);
    }

    @ExceptionHandler(ExceptionConflict.class)
    public ResponseEntity<ResponseError> exceptionConflict(Exception ex){
        ResponseError responseErrror = new ResponseError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now());
        logger.error("Conflict Exception: ",ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseErrror);
    }
}
