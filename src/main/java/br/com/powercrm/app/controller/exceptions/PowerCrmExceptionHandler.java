package br.com.powercrm.app.controller.exceptions;

import br.com.powercrm.app.dto.response.FieldErrorResponseDto;
import br.com.powercrm.app.dto.response.StandardErrorResponseDto;
import br.com.powercrm.app.service.exceptions.InvalidParamException;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.utils.http.HttpHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static br.com.powercrm.app.utils.http.HttpHelper.*;

@ControllerAdvice
public class PowerCrmExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponseDto> handleValidationException(MethodArgumentNotValidException e,
                                                                              HttpServletRequest httpServletRequest){
    Set<FieldErrorResponseDto> errors = getErrorsFromValidation(e);
    StandardErrorResponseDto standardErrorResponseDto = makeStandardErrorResponseDto(
            HttpHelper.getStatusCodeValue(HttpStatus.BAD_REQUEST),  HttpHelper.getPathUrlFromRequest(httpServletRequest),
            "Hibernate Validation Exception", "Please validate the errors_field to validate the payload",
            errors);
    return badRequest(standardErrorResponseDto);
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardErrorResponseDto> handleEntityAlreadyExistsException(
            ResourceAlreadyExistsException exception, HttpServletRequest httpServletRequest){
        StandardErrorResponseDto standardErrorResponseDto = makeStandardErrorResponseDto(
                HttpHelper.getStatusCodeValue(HttpStatus.UNPROCESSABLE_ENTITY), HttpHelper.getPathUrlFromRequest(httpServletRequest),
                "Entity already exists exception", exception.getMessage(), new HashSet<>());
        return unprocessedEntity(standardErrorResponseDto);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorResponseDto> handleEntityNotFoundException(
            ResourceNotFoundException exception, HttpServletRequest httpServletRequest){
        StandardErrorResponseDto standardErrorResponseDto = makeStandardErrorResponseDto(
                HttpHelper.getStatusCodeValue(HttpStatus.NOT_FOUND), HttpHelper.getPathUrlFromRequest(httpServletRequest),
                "Entity not found!", exception.getMessage(), new HashSet<>());
        return  notFound(standardErrorResponseDto);
    }

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<StandardErrorResponseDto> handleInvalidParamException(InvalidParamException exception,
                                                                                HttpServletRequest httpServletRequest){
        StandardErrorResponseDto standardErrorResponseDto = makeStandardErrorResponseDto(
                HttpHelper.getStatusCodeValue(HttpStatus.NOT_FOUND), HttpHelper.getPathUrlFromRequest(httpServletRequest),
                "Entity not found!", exception.getMessage(), new HashSet<>());
        return  badRequest(standardErrorResponseDto);
    }



    private static Set<FieldErrorResponseDto> getErrorsFromValidation(MethodArgumentNotValidException e){
        Set<FieldErrorResponseDto> errors = new HashSet<>();
        e.getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String description = error.getDefaultMessage();
            errors.add(new FieldErrorResponseDto(fieldName, description));
        });
        return errors;
    }

    private static StandardErrorResponseDto makeStandardErrorResponseDto(
            int statusCode, String pathUrl, String exception,
            String message, Set<FieldErrorResponseDto> errors){
        return new StandardErrorResponseDto(LocalDateTime.now(), statusCode, pathUrl, exception, message, errors);
    }
}
