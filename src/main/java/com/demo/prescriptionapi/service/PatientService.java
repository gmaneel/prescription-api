package com.demo.prescriptionapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.prescriptionapi.entity.Patient;
import com.demo.prescriptionapi.repo.PatientRepository;

@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;
	
	public Patient validate(Patient patient) {
		return patientRepository.findByNameAndGrp(patient.getName(),patient.getGrp());
	}
	
	public Patient save(Patient patient) {
		patientRepository.save(patient);
		return patientRepository.findByNameAndGrp(patient.getName(),patient.getGrp());
	}
	
	public Patient retrievePatient(String id) {
		Optional<Patient> o_patient = patientRepository.findById(id); 
		return o_patient.isPresent() ? o_patient.get() : null;
	}
}
