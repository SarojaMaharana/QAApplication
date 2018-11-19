package com.stackroute.auth.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.auth.constant.Url;
import com.stackroute.auth.exceptions.UserAlreadyExistsException;
import com.stackroute.auth.exceptions.UserNotFoundException;
import com.stackroute.auth.model.User;
import com.stackroute.auth.service.impl.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	private transient MockMvc mockMvc;

	@MockBean
	private transient UserService userService;

	@Mock
	private transient User user;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private String userJson;
	
	@Before
	public void init() throws JsonProcessingException {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		user= new User(1, "saroj@gmail.com", "password", "Saroj", "Maharana", new Date("22/11/2018"));
		ObjectMapper mapper = new ObjectMapper();
		userJson = mapper.writeValueAsString(user);
	}
	
	/**
	 * This method to check the register user.
	 * @throws UserAlreadyExistsException
	 * @throws Exception
	 */
	@Test
	public void registerUserTest() throws UserAlreadyExistsException, Exception {
		when(userService.registerUser(user)).thenReturn(true);
		mockMvc.perform(post(Url.REGISTER_URL).contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(MockMvcResultMatchers.status().isCreated());
		verify(userService,times(1)).registerUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
	

	/**
	 * Register user exception test. 
	 * @throws UserAlreadyExistsException
	 * @throws Exception
	 */
	@Test
	public void registerUserExceptionTest() throws UserAlreadyExistsException, Exception {
		when(userService.registerUser(user)).thenThrow(UserAlreadyExistsException.class);
		mockMvc.perform(post(Url.REGISTER_URL).contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(MockMvcResultMatchers.status().isBadRequest());
		verify(userService,times(1)).registerUser(Mockito.any(User.class));
		verifyZeroInteractions(userService);
	}
	
	/**
	 * Login test.
	 * @throws UserNotFoundException
	 * @throws Exception
	 */
	@Test
	public void loginTest() throws UserNotFoundException, Exception {
		when(userService.login("saroj@gmail.com","password")).thenReturn("token");
		mockMvc.perform(post(Url.LOGIN_URL).contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(userService,times(1)).login("saroj@gmail.com","password");
		verifyNoMoreInteractions(userService);
	}	
}
