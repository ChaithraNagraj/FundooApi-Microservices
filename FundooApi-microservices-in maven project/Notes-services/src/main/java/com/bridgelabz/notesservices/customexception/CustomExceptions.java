package com.bridgelabz.notesservices.customexception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.bridgelabz.notesservices.response.ErrorResponse;
/**
 * @ControllerAdvice is not specific to the exception handling , its also used for handling property, 
 * validation or formatter bindings at the global level. @ControllerAdvice in the context of exception handling is 
 * just another way of doing exception handling at a global level using @Exceptionhandler annotation.
 * @author chaithra B N
 *
 */
@ControllerAdvice
public class CustomExceptions extends ResponseEntityExceptionHandler{

	@ExceptionHandler(NoteNotFoundException.class)
	public final ResponseEntity<ErrorResponse> noteNotfoundExceptions(NoteNotFoundException e){
		ErrorResponse ex=new ErrorResponse();
		ex.setMessage(e.getMessage());
		ex.setStatusCode(e.getStatusCode());
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(),ex.getStatusCode()));
		
	}
}
