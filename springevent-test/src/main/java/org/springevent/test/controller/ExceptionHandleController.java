package org.springevent.test.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springevent.test.exception.NotRuntimeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandleController {
	
	@ExceptionHandler({ NotRuntimeException.class})
    public void handleException(HttpServletRequest request, HttpServletResponse response) {
        log.warn("get in ExceptionHandleController handleException");
        try {
			response.sendError(501, "ExceptionHandleController");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
