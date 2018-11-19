package com.stackroute.auth.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stackroute.auth.exceptions.UserAlreadyExistsException;
import com.stackroute.auth.exceptions.UserNotExistsException;
import com.stackroute.auth.exceptions.UserNotFoundException;
import com.stackroute.auth.model.User;
import com.stackroute.auth.repository.UserRepository;
import com.stackroute.auth.token.JWTTokenGenerator;

@Service
public class UserService{
    Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;
    
    @Autowired
    private JWTTokenGenerator  tokenGenerator;

    /**
     * This method will register user.
     * @param user
     * @throws UserAlreadyExistsException
     */
    public boolean registerUser(User user) throws UserAlreadyExistsException{
        log.info(" Inside register user Service. ");
        
            if(null==user) {
                log.error(env.getProperty("auth.user.cannot.be.null"));
            }
            // Check Email Id is already registered..
            Optional<User> userExists = userRepository.findByEmail(user.getEmail());

            if(userExists.isPresent()){
                throw new UserAlreadyExistsException(" User Already Exists ");
                
            }else{
	            try{
	                user = userRepository.save(user);
		        }catch(Exception exception){
		            log.error(" Exception occured during user save ");
		            return false;
		        }
                if(null==user) {
                    return false;
                }
            }
       
        return true;
    }

    /**
     * This method will check login username/password.
     * @param email
     * @param password
     * @return
     * @throws UserNotExistsException
     */
    public String login(String email, String password) throws UserNotFoundException{
        log.info(" Inside login service methiod ");
        String jwtToken="";
        try{
            User user = userRepository.findByEmailAndPassword(email, password);

            if(user == null){
                throw new UserNotFoundException("  User Not found Exception");
            }
        
            jwtToken= tokenGenerator.generateToken(user);
        
            log.info(" jwtToken "+jwtToken);
        }catch(Exception e){
            log.error(" Unexpected error coccured ");
        }
        return jwtToken;
    }

    /**
     * This method will find user details based on email provided.
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public User findByEmail(String email) throws UserNotExistsException{
        log.info(" Inside findbyemail service method. ");
        Optional<User>  userOptional = null;
        try{

            userOptional = userRepository.findByEmail(email) ;

            if(!userOptional.isPresent()){
                log.error(env.getProperty("auth.user.cannot.be.null"));
                throw new UserNotExistsException("User donesnt exists ");
            }
        }catch(Exception e){
            log.error(" Database Exception occured.");
        }
        return userOptional.get();
    }
    /**
     * Update user details. 
     * @param user
     * @return
     * @throws UserNotFoundException
     */
    public boolean updateUser(User user) throws UserNotFoundException{
        log.info(" Inside updateUser service methiod ");
        if(null==user) {
            log.error(env.getProperty("auth.user.cannot.be.null"));
			throw new UserNotFoundException(env.getProperty("auth.user.cannot.be.null"));
		}
		Optional<User> userOpt =  userRepository.findByEmail(user.getEmail());
		if(!userOpt.isPresent()) {
            log.error(env.getProperty("auth.user.not.found.with.email"));
			throw new UserNotFoundException(env.getProperty("auth.user.not.found.with.email")+user.getEmail());
		}
		User userDetails = userOpt.get();
        userDetails = userRepository.save(user);
        
		if(null==userDetails) {
			return false;
		}
		return true;
    }

    /**
     * Delete a user
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public boolean deleteUser(String email) throws UserNotFoundException{
        log.info(" Inside deleteUser service method ");
        if(null==email) {
           throw new UserNotFoundException(env.getProperty("auth.user.email.cannot.be.null"));
		}
		Optional<User> userOpt =  userRepository.findByEmail(email);
		if(!userOpt.isPresent()) {
           throw new UserNotFoundException(env.getProperty("auth.user.not.found.with.email ")+email);
		}
		User user = userOpt.get();
		userRepository.delete(user);
		return true;
    }
}