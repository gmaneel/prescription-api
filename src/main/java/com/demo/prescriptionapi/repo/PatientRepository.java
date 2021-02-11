package com.demo.prescriptionapi.repo;

import org.springframework.data.repository.CrudRepository;

import com.demo.prescriptionapi.entity.Patient;

public interface PatientRepository extends CrudRepository<Patient, String> {
	
	public Patient findByNameAndGrp(String name, String grp);

}
