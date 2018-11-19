package com.stackroute.auth.token;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.stackroute.auth.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JWTTokenGenerator{

	@Autowired
	private Environment env;

	/**
	 * Generate the token of a given user Object.
	 * @param user
	 * @return
	 */
	public String generateToken(User user) {
		// generate subject with email:id:firstname:lastname
		String subject=user.getEmail()+":"+user.getId()+":"+user.getFirstName()+":"+user.getLastName();

		String jwtToken=Jwts.builder()
				  .setIssuer("Stackroute")
				  .setSubject(subject)
				  .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
				  .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
				  .signWith(
				    SignatureAlgorithm.HS256,
				    TextCodec.BASE64.decode(env.getProperty("auth.token.secret.key"))
				  )
				  .compact();
		return jwtToken;
	}
}
