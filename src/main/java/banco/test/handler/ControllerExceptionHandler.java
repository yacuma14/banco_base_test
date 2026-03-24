package banco.test.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import banco.test.bean.ErrorMessage;
import banco.test.exception.ExistPagoUsuarioException;
import banco.test.exception.NotFoundPagoException;

@RestControllerAdvice 
public class ControllerExceptionHandler {

  
  @ExceptionHandler(NotFoundPagoException.class)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(NotFoundPagoException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.CONFLICT.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
  }
  
  

  @ExceptionHandler(ExistPagoUsuarioException.class)
  public ResponseEntity<ErrorMessage> ExistUserSyncException(ExistPagoUsuarioException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
  }
  

  @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
  public ResponseEntity<ErrorMessage> EntityNotFoundException(jakarta.persistence.EntityNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }

  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error) -> {
          String fieldName = ((FieldError) error).getField();
          String errorMessage = error.getDefaultMessage();
          errors.put(fieldName.toString(), errorMessage);
      });
      return errors;
  }
  
}
