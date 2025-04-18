package br.com.powercrm.app.controller.exceptions;

import br.com.powercrm.app.dto.response.FieldErrorResponseDto;
import br.com.powercrm.app.dto.response.StandardErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class PowerCrmExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponseDto> handleValidationException(MethodArgumentNotValidException e,
                                                                              HttpServletRequest httpServletRequest){
    Set<FieldErrorResponseDto> errors = new HashSet<>();
     e.getFieldErrors().forEach(error -> {
         String fieldName = error.getField();
         String description = error.getDefaultMessage();
         errors.add(new FieldErrorResponseDto(fieldName, description));
     });
    String pathUrl = httpServletRequest.getRequestURI();
    int badRequestStatusCode = HttpStatus.BAD_REQUEST.value();
    StandardErrorResponseDto standardErrorResponseDto = new StandardErrorResponseDto(LocalDateTime.now(),
            badRequestStatusCode, pathUrl, "Hibernate Validation Exception",
            "Please validate the errors_field to validate the payload", errors);
    return ResponseEntity.status(badRequestStatusCode).body(standardErrorResponseDto);
    }
}
