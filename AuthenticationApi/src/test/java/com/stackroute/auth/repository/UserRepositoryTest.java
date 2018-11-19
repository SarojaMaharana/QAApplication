package com.stackroute.auth.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.auth.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private transient UserRepository userRepository;
	
	private transient User user;
	
	@Before
	public void init() {
		user= new User(1,"saroj@gmail.com", "Saroj", "Maharana", "password", new Date());
	}
	
	@Test
	public void registerUserTest() {
		userRepository.save(user);
		List<User> userList= userRepository.findAll();
		final  User finalUser=userList.get(0);
		assertEquals("saroj@gmail.com", finalUser.getEmail());
	}
	
	
	@Test
	public void findUserByNameAndPassword() {
		userRepository.save(user);
		List<User> userList= userRepository.findAll();
		final  User finalUser=userList.get(0);
		assertEquals(user.getEmail(), finalUser.getEmail());
	}
	
}
