package com.demo.prescriptionapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.prescriptionapi.constants.ApplicationConstants;
import com.demo.prescriptionapi.entity.Drug;
import com.demo.prescriptionapi.model.ResponseModel;
import com.demo.prescriptionapi.service.DrugService;

@RestController
@RequestMapping("/drugs")
public class DrugController {
	
	@Autowired
	private DrugService drugService;
	
	@PostMapping("/validate")
	public ResponseModel validateDrug(@RequestBody Drug drug) {
		ResponseModel responseModel = null;
		Drug validDrug = drugService.validate(drug);
		responseModel = validDrug == null ?
				new ResponseModel(
						ApplicationConstants.DRUG_INVALID_CODE,
						ApplicationConstants.DRUG_INVALID_MESSAGE)
					: new ResponseModel(
							ApplicationConstants.DRUG_VALID_CODE,
							ApplicationConstants.DRUG_VALID_MESSAGE); ; 
		return responseModel;
	}
	
	@PostMapping(value="/save",consumes = {"application/json"})
	public Drug saveDrug(@RequestBody Drug drug) {
		return drugService.save(drug);
	}
	
	@GetMapping("/id/{id}")
	public Drug retrieveDrug(@PathVariable String id) {
		return drugService.retrieve(id);
	}
}
