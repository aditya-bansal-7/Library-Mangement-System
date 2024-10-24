package com.adityabansal7.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ExceptionDetails> myRouteException(UserException userException, WebRequest webRequest){
		
		ExceptionDetails error = new ExceptionDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(userException.getMessage());
		error.setDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BookException.class)
	public ResponseEntity<ExceptionDetails> myRouteException(BookException bookException, WebRequest webRequest){
		
		ExceptionDetails error = new ExceptionDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(bookException.getMessage());
		error.setDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(error, HttpStatus.BAD_REQUEST);
		
	}
	
}
