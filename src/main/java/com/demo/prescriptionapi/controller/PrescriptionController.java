package com.demo.prescriptionapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.prescriptionapi.model.PrescriptionModel;
import com.demo.prescriptionapi.model.ResponseModel;
import com.demo.prescriptionapi.service.PrescriptionService;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	@PostMapping("/save")
	public ResponseModel savePrescription(@RequestBody PrescriptionModel prescriptionModel) {
		return prescriptionService.save(prescriptionModel);
	}
	
	@GetMapping("/reference/{reference_id}")
	public PrescriptionModel retrievePresecriptionIdFromReference(@PathVariable String reference_id) {
		return prescriptionService.retrievePrescription(reference_id);
	}
	
	@GetMapping("/id/{id}")
	public PrescriptionModel retrievePrescriptiondetails(@PathVariable String id) {
		return prescriptionService.retrievePrescriptionDetails(id);
	}
}
