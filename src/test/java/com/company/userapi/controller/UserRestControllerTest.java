package com.company.userapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	//private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjQ0MjgyODQ3LCJpYXQiOjE2NDQyNjQ4NDd9.5bqnusA7TTZ45eVh22YyagBwfeY6qxlSgMfE9Is_T05BQ9tT07Ybl57_mGB-Plwg_QCuOzsVZbTq9T96pp8v4g";
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
		String requestJson = "{\"name\": \"Pablo\",\"email\": \"" + this.userEmail + "\",\"password\": \"pass\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity, UserDTO.class);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);	

		String requestJson2 = "{"
				+ "\"username\":\"" + userEmail + "\","
				+ "\"password\":\"pass\""
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
		String requestJson = "{\"name\": \"Pablo\",\"email\": \"" + userNameToCreate + "\",\"password\": \"pass\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(getRootUrl() + "/api/v1/register/", HttpMethod.POST, entity, UserDTO.class);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);	

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

/*	
	@Test
	void testGetUserById() {
		fail("Not yet implemented");
	}

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
