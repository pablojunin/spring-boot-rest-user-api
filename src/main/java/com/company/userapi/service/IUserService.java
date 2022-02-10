package com.company.userapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.company.userapi.model.User;

public interface IUserService {

	Iterable<User> findAll();

	Optional<User> findById(UUID id);

	List<User> findByEmail(String email);

	User save(User user);
	
	User update(User user);

	void deleteById(UUID id);

	void deleteAll();

	void addLogin(User user, String token);

	User getUser(String username);

}
