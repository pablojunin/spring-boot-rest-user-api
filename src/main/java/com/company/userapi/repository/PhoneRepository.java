package com.company.userapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.userapi.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long>{
}
