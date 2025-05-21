package com.sp.boot.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sp.boot.dto.ErrorResponse;
import com.sp.boot.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	 @ExceptionHandler(CustomException.class)
	    public ResponseEntity<ErrorResponse> handleLoginFail(CustomException e) {
	        
		 	ErrorResponse error = new ErrorResponse(e.getStatusCode(),"요청 오류",e.getMessage());

		 	return ResponseEntity.status(e.getStatusCode()).body(error);
		 
	    }
	
	

}
