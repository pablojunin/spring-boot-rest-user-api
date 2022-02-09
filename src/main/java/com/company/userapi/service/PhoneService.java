package com.company.userapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.userapi.model.Phone;
import com.company.userapi.repository.PhoneRepository;

@Service
public class PhoneService implements IPhoneService {

	@Autowired
	PhoneRepository phoneRepository;
	
	@Override
	public Phone save(Phone phone) {
		return phoneRepository.save(phone);
	}

}
