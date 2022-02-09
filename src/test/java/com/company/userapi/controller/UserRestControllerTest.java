package com.company.userapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.company.userapi.UserApiApplication;
import com.company.userapi.dto.AuthenticateDTO;
import com.company.userapi.dto.UserDTO;
import com.company.userapi.dto.UserTestDTO;
import com.company.userapi.model.User;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {

	private static final Random RANDOM = new Random();
	
	@Autowired
	private TestRestTemplate restTemplate;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@LocalServerPort
	private int port;
	private String currentToken = "";
	private String userEmail = "";

	private Integer count;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}	
	
	@BeforeEach
	void setUp() throws Exception {
		
		this.count = RANDOM.nextInt();
		this.userEmail = "poyarzabal" + this.count.toString() + "@gmail.com";
		String requestJson = "{\"name\": \"Pablo\",\"email\": \"" + this.userEmail + "\",\"password\": \"passpass\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity, UserDTO.class);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);	

		String requestJson2 = "{"
				+ "\"username\":\"" + userEmail + "\","
				+ "\"password\":\"passpass\""
				+ "}";

		HttpHeaders headers2 = new HttpHeaders();
		headers2.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity2 = new HttpEntity<String>(requestJson2, headers2);
		ResponseEntity<AuthenticateDTO> response2 = restTemplate.exchange(getRootUrl() + "/authenticate", HttpMethod.POST, entity2, AuthenticateDTO.class);
		
		Assertions.assertEquals(response2.getStatusCode(), HttpStatus.OK);	
		
		this.currentToken = response2.getBody().getToken();
		
	}

		
	@Test
	void testCreateUser() {
		String userNameToCreate = "poyarzabaleduardo@gmail.com";
		String requestJson = "{\"name\": \"Pablo\",\"email\": \"" + userNameToCreate + "\",\"password\": \"passpass\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity, UserDTO.class);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);	

	}
	
	@Test
	void testCreateUserWithErrors() {
		
		//username empty
		String userNameToCreate = "";
		String requestJson = "{\"name\": \"Pablo\",\"email\": \"" + userNameToCreate + "\",\"password\": \"passpass\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity, UserDTO.class);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);	
		Assertions.assertEquals(response.getBody().getMessage(), "email - no debe estar vacío");	

		//pass empty
		String userNameToCreate2 = "poyarzabaleduardo@gmail.com";
		String requestJson2 = "{\"name\": \"Pablo\",\"email\": \"" + userNameToCreate2 + "\",\"password2\": \"\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity2 = new HttpEntity<String>(requestJson2, headers2);
		ResponseEntity<UserDTO> response2 = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity2, UserDTO.class);
		
		Assertions.assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST);	
		Assertions.assertEquals(response2.getBody().getMessage(), "password - no debe estar vacío");	

		//pass empty
		String userNameToCreate3 = "poyarzabaleduardo@gmail.com";
		String requestJson3 = "{\"name\": \"Pablo\",\"email\": \"" + userNameToCreate3 + "\",\"password\": \"pass\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";
		
		HttpHeaders headers3 = new HttpHeaders();
		headers3.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity3 = new HttpEntity<String>(requestJson3, headers3);
		ResponseEntity<UserDTO> response3 = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity3, UserDTO.class);
		
		Assertions.assertEquals(response3.getStatusCode(), HttpStatus.BAD_REQUEST);	
		Assertions.assertEquals(response3.getBody().getMessage(), "password - password should have at least 8 characters");	

		//pass empty
		String userNameToCreate4 = "poyarzabaleduardoarrobagmail.com";
		String requestJson4 = "{\"name\": \"Pablo\",\"email\": \"" + userNameToCreate4 + "\",\"password\": \"passpass\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";
		
		HttpHeaders headers4 = new HttpHeaders();
		headers4.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity4 = new HttpEntity<String>(requestJson4, headers4);
		ResponseEntity<UserDTO> response4 = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity4, UserDTO.class);
		
		Assertions.assertEquals(response4.getStatusCode(), HttpStatus.BAD_REQUEST);	
		Assertions.assertEquals(response4.getBody().getMessage(), "email - debe ser una dirección de correo electrónico con formato correcto");			
	}
	
	@Test
	void testGetAllUsers() {
		HttpHeaders headers3 = new HttpHeaders();
		headers3.setContentType(MediaType.APPLICATION_JSON);
		headers3.set("Authorization", "Bearer "+ currentToken);
		HttpEntity<String> entity3 = new HttpEntity<String>(null, headers3);
		
		ResponseEntity<ArrayList> response3 = restTemplate.exchange(getRootUrl() + "/api/v1/users/",
				HttpMethod.GET, entity3, ArrayList.class);

		Boolean newOk = false;
		List<User> list = new ArrayList();
		for (Object i : response3.getBody()) {
			User userTemp = modelMapper.map(i, User.class);
			newOk = (userTemp.getEmail().equals(this.userEmail))? true: newOk;
			list.add(userTemp);
		}
		
		Assertions.assertNotNull(response3.getBody());
		Assertions.assertEquals(response3.getStatusCode(), HttpStatus.OK);	
		Assertions.assertTrue(newOk);
	}

	@Test
	void testGetUserById() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ currentToken);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<ArrayList> response = restTemplate.exchange(getRootUrl() + "/api/v1/users/",
				HttpMethod.GET, entity, ArrayList.class);

		Boolean newOk = false;
		String uuid = null;
		List<UserTestDTO> list = new ArrayList<UserTestDTO>();
		for (Object i : response.getBody()) {
			UserTestDTO userTemp = modelMapper.map(i, UserTestDTO.class);
			newOk = (userTemp.getEmail().equals(this.userEmail))? true: newOk;
			if (newOk) {
				uuid = userTemp.getId();
			}
			list.add(userTemp);
		}
		
		Assertions.assertNotNull(response.getBody());
		Assertions.assertNotNull(uuid);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);	
		Assertions.assertTrue(newOk);
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.setContentType(MediaType.APPLICATION_JSON);
		headers2.set("Authorization", "Bearer "+ currentToken);
		HttpEntity<String> entity2 = new HttpEntity<String>(null, headers2);
		
		ResponseEntity<UserTestDTO> response2 = restTemplate.exchange(getRootUrl() + "/api/v1/users/" + uuid,
				HttpMethod.GET, entity2, UserTestDTO.class);

		Assertions.assertNotNull(response2.getBody());
		Assertions.assertEquals(response2.getStatusCode(), HttpStatus.OK);	
		Assertions.assertEquals(response2.getBody().getId(), uuid);	
		
		
	}

	/*
	@Test
	void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteAllUsers() {
		fail("Not yet implemented");
	}
*/
}
