package com.company.userapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
	private String number;
	private String citycode;
	private String countrycode;
	
	public Phone() {
		super();
	}

	public Phone(Long id, String number, String citycode, String countrycode) {
		super();
		this.id = id;
		this.number = number;
		this.citycode = citycode;
		this.countrycode = countrycode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	@Override
	public String toString() {
		return "Phone [id=" + id + ", number=" + number + ", citycode=" + citycode + ", contrycode=" + countrycode + "]";
	}
	
}
