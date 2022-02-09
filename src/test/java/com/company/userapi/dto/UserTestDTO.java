package com.company.userapi.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.company.userapi.model.Phone;

public class UserTestDTO {

    private String id;
    
    @NotBlank
	private String name;
    
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	//@Size(min = 8, message = "password should have at least 8 characters")
	@Pattern(regexp="^[a-zA-Z0-9]{8,}",message="password should have at least 8 characters")
	private String password;
	private String token;
	private Boolean isactive;
	private Date lastLogin;
	private List<Phone> phones = new ArrayList<Phone>();
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserTestDTO() {
		super();
	}

	public UserTestDTO(String id, String name, String email, String password, String token, Boolean isactive, Date lastLogin,
			List<Phone> phones) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.token = token;
		this.isactive = isactive;
		this.lastLogin = lastLogin;
		this.phones = phones;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}


	public List<Phone> getPhones() {
		return phones;
	}


	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	};
	
}
