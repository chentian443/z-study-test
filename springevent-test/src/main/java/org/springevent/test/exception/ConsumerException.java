package org.springevent.test.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConsumerException extends RuntimeException{
	
	public ConsumerException(){
		super();
	}
	
	public ConsumerException(String message){
		super(message);
	}
}
