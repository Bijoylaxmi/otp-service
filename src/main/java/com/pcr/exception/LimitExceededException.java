package com.pcr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LimitExceededException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LimitExceededException(String message) {
        super(message);
    }

}
