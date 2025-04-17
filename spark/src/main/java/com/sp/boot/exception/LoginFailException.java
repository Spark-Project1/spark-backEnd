package com.sp.boot.exception;

public class LoginFailException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public LoginFailException(String message) {
        super(message);
    }
    

}
