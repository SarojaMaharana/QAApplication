package com.stackroute.auth.repository;

import java.util.Optional;

import com.stackroute.auth.exceptions.UserNotExistsException;
import com.stackroute.auth.exceptions.UserNotFoundException;
import com.stackroute.auth.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{

    /**
     * This method will check the provided email and password against DB.
     * @param email
     * @return
     */
	public User findByEmailAndPassword(String email, String password);

    /**
     * This method will return user details based on provided email.
     * @param email
     * @return
     */
	public Optional<User> findByEmail(String email);
	
}

