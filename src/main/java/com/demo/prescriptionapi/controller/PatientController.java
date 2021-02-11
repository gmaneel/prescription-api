package com.demo.prescriptionapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.prescriptionapi.constants.ApplicationConstants;
import com.demo.prescriptionapi.entity.Patient;
import com.demo.prescriptionapi.model.ResponseModel;
import com.demo.prescriptionapi.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/validate")
	public ResponseModel validatePatient(@RequestBody Patient patient) {
		ResponseModel responseModel = null;
		Patient validPatient = patientService.validate(patient);
		responseModel = validPatient == null ?
							new ResponseModel(
									ApplicationConstants.PATIENT_INVALID_CODE,
									ApplicationConstants.PATIENT_INVALID_MESSAGE)
								: new ResponseModel(
										ApplicationConstants.PATIENT_VALID_CODE,
										ApplicationConstants.PATIENT_VALID_MESSAGE); 
		return responseModel;
	}
	
	@PostMapping("/save")
	public Patient savePatient(@RequestBody Patient patient) {
		return patientService.save(patient);
	}
	
	@GetMapping("/id/{id}")
	public Patient retrievePatient(@PathVariable String id) {
		return patientService.retrievePatient(id);
	}
}
