package com.demo.prescriptionapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Prescription {
	
	@Id
	String id;
	
	String drugid;
	
	String patientid;
	
	String refid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDrugid() {
		return drugid;
	}
	public void setDrugid(String drugid) {
		this.drugid = drugid;
	}
	public String getPatientid() {
		return patientid;
	}
	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getRefid() {
		return refid;
	}
	public void setRefid(String refid) {
		this.refid = refid;
	}
}
