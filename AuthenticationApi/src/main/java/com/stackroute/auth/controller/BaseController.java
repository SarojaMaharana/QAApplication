package com.stackroute.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stackroute.auth.model.ResponseMessage;

public class BaseController{

    final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * Handle response.
     * @param message
     * @param status
     * @return
     */
    @ResponseBody
	public ResponseEntity<ResponseMessage> handleResponse(String message, HttpStatus status) {
		logger.info("handleResponse");
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(message), status);
    }
}