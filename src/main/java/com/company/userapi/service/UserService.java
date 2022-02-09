package com.company.userapi.service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.userapi.model.User;
import com.company.userapi.repository.UserRepository;

@Service
public class UserService implements IUserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public List<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(UUID id) {
		userRepository.deleteById(id);		
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public void addLogin(User user, String token) {
		user.setToken(token);
		user.setLastLogin(Calendar. getInstance(). getTime());
		userRepository.save(user);
	}

	public User getUser(String username) {
		List<User> users = this.findByEmail(username);
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
	
}
