package com.company.userapi.jwt.config.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.userapi.exception.LoginProblemException;
import com.company.userapi.jwt.config.model.JwtRequest;
import com.company.userapi.jwt.config.model.JwtResponse;
import com.company.userapi.jwt.config.service.JwtUserDetailsService;
import com.company.userapi.jwt.config.util.JwtTokenUtil;
import com.company.userapi.model.User;
import com.company.userapi.service.IUserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		User userTemp = userService.getUser(authenticationRequest.getUsername());
		if (userTemp != null && authenticate(userTemp, authenticationRequest.getPassword())) {

			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());

			final String token = jwtTokenUtil.generateToken(userDetails);
			
			userService.addLogin(userTemp, token);
			
			return ResponseEntity.ok(new JwtResponse(token));			
		}
		throw new LoginProblemException(authenticationRequest.getUsername());

	}

	private boolean authenticate(User user, String password) throws Exception {
		
		return user != null && user.getPassword().equals(password);

	}
	
}