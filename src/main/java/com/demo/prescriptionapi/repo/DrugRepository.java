package com.demo.prescriptionapi.repo;

import org.springframework.data.repository.CrudRepository;

import com.demo.prescriptionapi.entity.Drug;

public interface DrugRepository extends CrudRepository<Drug, String> {
	
	public Drug findByIdAndName(String id, String name);

}
