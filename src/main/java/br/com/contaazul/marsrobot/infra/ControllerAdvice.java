package br.com.contaazul.marsrobot.infra;

import br.com.contaazul.marsrobot.dto.ErrorResponseDTO;
import br.com.contaazul.marsrobot.dto.ErrorValidationResponseDTO;
import br.com.contaazul.marsrobot.exception.ColisionException;
import br.com.contaazul.marsrobot.exception.InactiveException;
import br.com.contaazul.marsrobot.exception.LimitMapException;
import br.com.contaazul.marsrobot.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({InactiveException.class, ColisionException.class, NotFoundException.class, LimitMapException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDTO> handleErrorResponse(RuntimeException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidationResponseDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();

        return ResponseEntity.badRequest()
                .body(fieldErrors.stream().map(ErrorValidationResponseDTO::new).toList());
    }
}
