package com.stackroute.auth.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.auth.exceptions.UserAlreadyExistsException;
import com.stackroute.auth.exceptions.UserNotFoundException;
import com.stackroute.auth.model.User;
import com.stackroute.auth.repository.UserRepository;
import com.stackroute.auth.service.impl.UserService;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@Mock
	private transient UserRepository userRepository;

	private transient User user;

	@InjectMocks
	private transient UserService userService;

	private transient Optional<User> options;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		user= new User(1,"saroj@gmail.com", "Saroj", "Maharana", "password", new Date("22/11/2018"));
		options = Optional.of(user);
	}

	@Test
	public void mockTest() {
		assertNotNull(" User object has been set up properly", user);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void registerUserFailureTest() throws UserAlreadyExistsException {
		when(userRepository.findByEmail("saroj@gmail.com")).thenReturn(options);
		userService.registerUser(user);
		verify(userRepository, times(1)).findByEmail(user.getEmail());
	}

	@Test
	public void registerUserSuccessTest() throws UserAlreadyExistsException {
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = userService.registerUser(user);
		assertTrue(" Should return false ", flag);
		verify(userRepository, times(1)).save(user);
		verify(userRepository, times(1)).findByEmail(user.getEmail());
	}

	@Test
	public void getUserLoginTest() throws UserNotFoundException {
		when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);
		userService.login(user.getEmail(), user.getPassword());
		verify(userRepository, times(1)).findByEmailAndPassword(user.getEmail(), user.getPassword());
	}

}