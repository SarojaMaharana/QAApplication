package com.stackroute.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.auth.constant.Url;
import com.stackroute.auth.exceptions.UserAlreadyExistsException;
import com.stackroute.auth.exceptions.UserNotFoundException;
import com.stackroute.auth.model.ResponseMessage;
import com.stackroute.auth.model.User;
import com.stackroute.auth.service.impl.UserService;

@RestController
@CrossOrigin
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private Environment env;

	/**
	 * This method will used to validate to login to system.
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws UserNotFoundException
	 */
	@PostMapping(value = Url.LOGIN_URL)
	public ResponseEntity<ResponseMessage> login(final @RequestBody User user)
			throws UserNotFoundException {
		logger.info(" inside login controller method.");

		String token = userService.login(user.getEmail(), user.getPassword());
		
		if (null == token) {
			return handleResponse(token, HttpStatus.UNAUTHORIZED);
		}
		return handleResponse(token, HttpStatus.OK);
	}

	/**
	 * This method will register user to QnA system.
	 * 
	 * @param newUser
	 * @return
	 */
	@PostMapping(value = Url.REGISTER_URL, consumes = "application/json")
	public ResponseEntity<ResponseMessage> register(@RequestBody User newUser) {
		logger.info(" inside signup controller method."+newUser);
		boolean status = false;
	
		if (null == newUser) {
			return handleResponse(env.getProperty("auth.user.cannot.be.null"), HttpStatus.BAD_REQUEST);
		}
		try {
			status = userService.registerUser(newUser);
		} catch (UserAlreadyExistsException e) {
			return handleResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (status) {
			return handleResponse(env.getProperty("auth.signup.successful") + " " + newUser.getEmail(),HttpStatus.CREATED);
		} else {
			return handleResponse(env.getProperty("auth.signup.failed") + " " + newUser.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
