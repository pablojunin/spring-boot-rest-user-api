package com.company.userapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.userapi.model.User;

public interface UserRepository extends JpaRepository<User, UUID>{
	Optional<User> findById(UUID id);
	List<User> findByName(String name);
	List<User> findByEmail(String email);
}
