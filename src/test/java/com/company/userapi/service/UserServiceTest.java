package com.company.userapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.company.userapi.UserApiApplication;
import com.company.userapi.exception.EmailAlreadyExistException;
import com.company.userapi.model.Phone;
import com.company.userapi.model.User;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

	@Autowired
	private IUserService userService;
	
	@Test
	void testFindAll() {
		String email = "poyarzabal1@gmail.com";
		User newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		email = "poyarzabal2@gmail.com";
		newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		email = "poyarzabal3@gmail.com";
		newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		email = "poyarzabal4@gmail.com";
		newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		List<User> users = new ArrayList<User>();

		userService.findAll().forEach(users::add);
		
		Assertions.assertNotNull(users);
		Assertions.assertEquals(users.size(), 4);
		
	}

	@Test
	void testFindByEmail() {
		String email = "poyarzabalbyemail@gmail.com";
		User newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		List<User> userStored = userService.findByEmail(email);
		
		Assertions.assertNotNull(userStored);
		Assertions.assertEquals(userStored.size(), 1);
	}

	@Test
	void testSave() {
		String email = "poyarzabalsave@gmail.com";
		User newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		User userStored = userService.getUser(email);
		
		Assertions.assertNotNull(userStored);
		Assertions.assertEquals(userStored.getEmail(), email);	

		User otherUserSameEmail = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		Boolean exceptionThown = false;
		try {
			userService.save(otherUserSameEmail);			
		} catch (EmailAlreadyExistException emailExist) {
			exceptionThown = true;
		}
		
		Assertions.assertNotNull(otherUserSameEmail);
		Assertions.assertEquals(otherUserSameEmail.getEmail(), email);	
		Assertions.assertTrue(exceptionThown);	
	}

	@Test
	void testUpdate() {
		String email = "poyarzabalupdate@gmail.com";
		User newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		User userStored = userService.getUser(email);
		
		Assertions.assertNotNull(userStored);
		Assertions.assertEquals(userStored.getEmail(), email);	
		//Assertions.assertTrue(newOk);

		String name = "Eduardo";
		userStored.setName(name);
		userService.update(userStored);
		
		userStored = userService.getUser(email);

		Assertions.assertNotNull(userStored);
		Assertions.assertEquals(userStored.getEmail(), email);	
		Assertions.assertEquals(userStored.getName(), name);
	}

	@Test
	void testDeleteById() {
		String email = "poyarzabaldelete@gmail.com";
		User newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		email = "poyarzabaldelete1@gmail.com";
		newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		User userStored = userService.getUser(email);
		
		userService.deleteById(userStored.getId());
		
		List<User> users = new ArrayList<User>();

		userService.findAll().forEach(users::add);
		
		Assertions.assertNotNull(users);
		Assertions.assertEquals(users.size(), 1);
	}

	@Test
	void testDeleteAll() {
		String email = "poyarzabaldeleteall@gmail.com";
		User newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		email = "poyarzabaldeleteall1@gmail.com";
		newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		userService.deleteAll();
		
		List<User> users = new ArrayList<User>();

		userService.findAll().forEach(users::add);
		
		Assertions.assertNotNull(users);
		Assertions.assertEquals(users.size(), 0);
	}

	@Test
	void testAddLogin() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUser() {
		String email = "poyarzabalgetuser@gmail.com";
		User newUser = new User("Pablo", email, "passpass", "", true, new ArrayList<Phone>());
		userService.save(newUser);
		
		User userStored = userService.getUser(email);
		
		Assertions.assertNotNull(userStored);
		Assertions.assertEquals(userStored.getEmail(), email);	
	}

}
