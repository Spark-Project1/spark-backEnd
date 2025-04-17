package com.sp.boot.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sp.boot.exception.LoginFailException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	 @ExceptionHandler(LoginFailException.class)
	    public Map<String, Object> handleLoginFail(LoginFailException message) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("error", "로그인 실패");
	        map.put("message", message.getMessage());
	        return map;
	    }
	
	

}
