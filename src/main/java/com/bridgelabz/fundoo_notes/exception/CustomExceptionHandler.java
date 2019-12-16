package com.bridgelabz.fundoo_notes.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoo_notes.responses.Response;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public  ResponseEntity<Response> handleAllExceptions(Exception ex) {
       
    	Response response=new Response(ex.getMessage(),400);
      
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }
	
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public final ResponseEntity<Response> handleContraintViolation(
//    		MethodArgumentNotValidException ex, WebRequest request){
//    	
//    	List<String> errors =ex.getBindingResult()
//    						.getFieldErrors()
//    						.stream()
//    						.map(x -> x.getDefaultMessage())
//    						.collect(Collectors.toList());
//    	
//    	Response error = new Response(HttpStatus.BAD_REQUEST.value(), errors);
//    	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
}
