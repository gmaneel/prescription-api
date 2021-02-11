package com.demo.prescriptionapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.prescriptionapi.entity.Drug;
import com.demo.prescriptionapi.repo.DrugRepository;

@Service
public class DrugService {
	
	@Autowired
	DrugRepository drugRepository;
	
	public Drug validate(Drug drug) {
		return drugRepository.findByIdAndName(drug.getId(),drug.getName());
	}
	
	public Drug save(Drug drug) {
		drugRepository.save(drug);
		return drugRepository.findByIdAndName(drug.getId(),drug.getName());
	}
	
	public Drug retrieve(String id) {
		Optional<Drug> o_drug = drugRepository.findById(id);
		return !o_drug.isEmpty() ? o_drug.get() : null;
	}
}
