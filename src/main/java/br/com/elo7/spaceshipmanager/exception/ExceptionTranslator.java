package br.com.elo7.spaceshipmanager.exception;


import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.elo7.spaceshipmanager.exception.model.ErrorConstants;
import br.com.elo7.spaceshipmanager.exception.model.ErrorDTO;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> processValidationError(MethodArgumentNotValidException ex) {
    	log.error("Exception handled", ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    private ErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ErrorDTO dto = new ErrorDTO(ErrorConstants.ERR_VALIDATION);
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> processValidationError(MissingServletRequestParameterException ex) {
    	log.error("Exception handled", ex);
    	ErrorDTO dto = new ErrorDTO(ErrorConstants.ERR_VALIDATION, ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> processValidationError(ConstraintViolationException ex) {
    	log.error("Exception handled", ex);
    	ErrorDTO dto = new ErrorDTO(ErrorConstants.ERR_VALIDATION, ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> processRuntimeException(Exception ex) {
        log.error("processRuntimeException: ", ex);
        ResponseEntity.BodyBuilder builder;
        ErrorDTO errorDTO;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorDTO = new ErrorDTO("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorDTO = new ErrorDTO(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return builder.body(errorDTO);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> processResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Error: ", ex);
        ErrorDTO errorDTO = new ErrorDTO(ErrorConstants.ERR_RESOURCE_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(LandingNotPermitedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> processResourceNotFoundException(LandingNotPermitedException ex) {
        log.error("Error: ", ex);
        ErrorDTO errorDTO = new ErrorDTO(ErrorConstants.ERR_VALIDATION, ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(OutsidePlanetException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> processResourceNotFoundException(OutsidePlanetException ex) {
        log.error("Error: ", ex);
        ErrorDTO errorDTO = new ErrorDTO(ErrorConstants.ERR_VALIDATION, ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
