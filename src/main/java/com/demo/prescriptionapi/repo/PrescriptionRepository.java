package com.demo.prescriptionapi.repo;

import org.springframework.data.repository.CrudRepository;

import com.demo.prescriptionapi.entity.Prescription;

public interface PrescriptionRepository extends CrudRepository<Prescription, String> {
	
	public Prescription findByRefid(String refid);

}
