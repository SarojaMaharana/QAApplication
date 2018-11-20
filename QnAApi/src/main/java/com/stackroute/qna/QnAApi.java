package com.stackroute.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.stackroute.qna.constant.Url;
import com.stackroute.qna.filter.JWTAuthFilter;

@SpringBootApplication
public class QnAApi {
	
	@Autowired
	Environment env;
	
	@Bean
	public FilterRegistrationBean<JWTAuthFilter> jwtFilter() {
		final FilterRegistrationBean<JWTAuthFilter> filterRegistration = new FilterRegistrationBean<JWTAuthFilter>();
		filterRegistration.setFilter(new JWTAuthFilter(env));
		filterRegistration.addUrlPatterns(Url.BASE_URL+"/*");
		return filterRegistration;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(QnAApi.class, args);
	}
}
