package com.company.userapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.userapi.dto.UserDTO;
import com.company.userapi.dto.UserDTOResponse;
import com.company.userapi.exception.EmailAlreadyExistException;
import com.company.userapi.model.Phone;
import com.company.userapi.model.User;
import com.company.userapi.service.IPhoneService;
import com.company.userapi.service.IUserService;

@RestController
@RequestMapping(path = "/api/v1", produces = { "application/json" })
@Validated
public class UserRestController {

	@Autowired
	private IPhoneService phoneService;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private ModelMapper modelMapper;

	private UserDTOResponse convertToDTO(User user) {
		UserDTOResponse userTemp = modelMapper.map(user, UserDTOResponse.class);
		return userTemp;
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTOResponse>> getAllUsers() {
		try {
			List<User> users = new ArrayList<User>();

			userService.findAll().forEach(users::add);

			if (users.isEmpty()) {
				//TODO add custom exception
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			List<UserDTOResponse> usersDTO = new ArrayList<UserDTOResponse>();
			for (User user : users) {
				usersDTO.add(this.convertToDTO(user));
			}

			return new ResponseEntity<>(usersDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserDTOResponse> getUserById(@PathVariable("id") UUID id) {
		Optional<User> userData = userService.findById(id);
		if (userData.isPresent()) {
			UserDTOResponse userTemp = this.convertToDTO(userData.get());
			return new ResponseEntity<>(userTemp, HttpStatus.OK);
		} else {
			//TODO add custom exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/register/")
	public ResponseEntity<UserDTOResponse> createUser(@Valid @RequestBody UserDTO user) {

		for (Phone phone : user.getPhones()) {
			phoneService.save(phone);
		}

		List<User> usersByEmail = userService.findByEmail(user.getEmail());
		if (usersByEmail.size() > 0) {
			throw new EmailAlreadyExistException(user.getEmail());
		}

		User _user = userService.save(new User(user.getName(), user.getEmail(), user.getPassword(), user.getToken(),
				user.getIsactive(), user.getPhones()));

		UserDTOResponse userTemp = this.convertToDTO(_user);
		return new ResponseEntity<>(userTemp, HttpStatus.CREATED);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<UserDTOResponse> updateUser(@PathVariable("id") UUID id, @RequestBody UserDTO user) {
		Optional<User> userData = userService.findById(id);

		for (Phone phone : user.getPhones()) {
			phoneService.save(phone);
		}

		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setEmail(user.getEmail());
			_user.setName(user.getName());
			_user.setPassword(user.getPassword());
			_user.setPhones(user.getPhones());

			User savedUser = userService.save(_user);
			UserDTOResponse userTemp = this.convertToDTO(savedUser);
			return new ResponseEntity<>(userTemp, HttpStatus.OK);
		} else {
			//TODO add custom exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") UUID id) {
		try {
			userService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			//TODO add custom exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users")
	public ResponseEntity<HttpStatus> deleteAllUsers() {
		try {
			userService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			//TODO add custom exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}