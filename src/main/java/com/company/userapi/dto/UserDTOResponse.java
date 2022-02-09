package com.company.userapi.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.company.userapi.model.Phone;

public class UserDTOResponse {

    private UUID id;
	private String name;
	private String email;
	private String token;
	private Boolean isactive;
	private Date created;
	private Date modified;
	private Date lastLogin;
	
	private List<Phone> phones = new ArrayList<Phone>();
	
	public UserDTOResponse() {
		super();
	}

	public UserDTOResponse(UUID id, @NotBlank String name, @NotEmpty @Email String email, String token,
			Boolean isactive, Date created, Date modified, Date lastLogin, List<Phone> phones) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.token = token;
		this.isactive = isactive;
		this.created = created;
		this.modified = modified;
		this.lastLogin = lastLogin;
		this.phones = phones;
	}

	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
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
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	};
	
}
