package kaioenzo.contabancaria.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> processarBaseException(BaseException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus())).body(converterParaProblemDetail(ex));
    }

    private ProblemDetail converterParaProblemDetail(BaseException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(ex.getStatus()), ex.getMessage());
    }
}