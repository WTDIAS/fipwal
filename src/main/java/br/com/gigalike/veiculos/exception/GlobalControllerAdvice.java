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

    @ExceptionHandler(FipewalException.class)
    public ResponseEntity<ResponseError> globalFipewalException(FipewalException ex){
        ResponseError responseErrror = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now());
        logger.warn("FipewalException: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseErrror);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> globalException(Exception ex){
        ResponseError responseErrror = new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Houve um erro interno no servidor. Tente novamente mais tarde. Se o problema persistir informe o administrador.",
                LocalDateTime.now());
        logger.error("Global Exception: ",ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseErrror);
    }



}
