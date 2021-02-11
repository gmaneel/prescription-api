package com.demo.prescriptionapi.service;

import org.springframework.stereotype.Service;

@Service
public class EnvironmentService {
	public String getDomainUrl() {
		//TODO: Modify this method to pull the profiles dynamically
		return "http://localhost:8080";
	}
}
