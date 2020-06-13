package org.springevent.test.exception;

public class NotRuntimeException extends Exception{

	public NotRuntimeException(){
		super();
	}
	
	public NotRuntimeException(String message){
		super(message);
	}
}
