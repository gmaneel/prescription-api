package com.demo.prescriptionapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Patient {
	
	public Patient() {
		
	}
	
	public Patient(String pid, String name, String grp) {
		this.pid = pid;
		this.name = name;
		this.grp = grp;
	}
	
	
	@Id
	String pid;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrp() {
		return grp;
	}
	public void setGrp(String grp) {
		this.grp = grp;
	}
	String name;
	String grp;
	
	

}
