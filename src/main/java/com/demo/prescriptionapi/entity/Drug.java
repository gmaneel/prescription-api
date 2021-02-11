package com.demo.prescriptionapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Drug {
	
	public Drug() {
	}
	
	public Drug(String id, String name, String number) {
		this.id = id;
		this.name = name;
		this.number = number;
	}
	@Id
	String id;
	String name;
	String number;
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
