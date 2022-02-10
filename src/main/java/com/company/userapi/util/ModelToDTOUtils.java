package com.company.userapi.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.userapi.dto.UserDTOResponse;
import com.company.userapi.model.User;

@Component
public class ModelToDTOUtils {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserDTOResponse convertToDTO(User user) {
		UserDTOResponse userTemp = modelMapper.map(user, UserDTOResponse.class);
		return userTemp;
	}

	public List<UserDTOResponse> convertToDTO(List<User> users) {
		List<UserDTOResponse> usersDTO = new ArrayList<UserDTOResponse>();
		for (User user : users) {
			usersDTO.add(this.convertToDTO(user));
		}
		return usersDTO;
	}
}
