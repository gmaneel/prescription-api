package com.demo.prescriptionapi.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.demo.prescriptionapi.constants.ApplicationConstants;
import com.demo.prescriptionapi.entity.Drug;
import com.demo.prescriptionapi.entity.Patient;
import com.demo.prescriptionapi.entity.Prescription;
import com.demo.prescriptionapi.model.PrescriptionModel;
import com.demo.prescriptionapi.model.ResponseModel;
import com.demo.prescriptionapi.repo.PrescriptionRepository;

@Service
public class PrescriptionService {
	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	private EnvironmentService environmentService;
	
	// @Autowired
	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${prescription.service.retry.count}")
	private int allowedNumberOfRetries;
	
	private HttpHeaders SYSTEM_HTTP_HEADERS = new HttpHeaders() {{
			String auth = "system" + ":" + "system";
			byte[] encoded = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String (encoded);
			set("Authorization",authHeader);
		}};
	
	
	private ResponseModel showSystemError() {
		return new ResponseModel(ApplicationConstants.SYSTEM_ERROR_CODE,
						ApplicationConstants.SYSTEM_ERROR_MESSAGE);
	}
	
	private HttpEntity<?> buildEntityWithAuth(){
		return new HttpEntity<>(SYSTEM_HTTP_HEADERS);
	}
	
	private HttpEntity<Patient> buildPatientHttpEntity(PrescriptionModel prescriptionModel) {
		Patient patient = new Patient(
				prescriptionModel.getPatient().getPid(), 
				prescriptionModel.getPatient().getName(),
				prescriptionModel.getPatient().getGrp()
				);
		return new HttpEntity<>(patient, SYSTEM_HTTP_HEADERS);
	}
	
	private HttpEntity<Drug> buildDrugHttpEntity(PrescriptionModel prescriptionModel){
		Drug drug = new Drug(
				prescriptionModel.getDrug().getId(),
				prescriptionModel.getDrug().getName(),
				prescriptionModel.getDrug().getNumber()
				);
		return new HttpEntity<>(drug, SYSTEM_HTTP_HEADERS);
	}
	
	private ResponseEntity<ResponseModel> validateDrugData(PrescriptionModel prescriptionModel, int attempts) {
		ResponseEntity<ResponseModel> responseFromDrugValidation = null;
		try {
			responseFromDrugValidation = restTemplate.
			exchange(environmentService.getDomainUrl()+"/drugs/validate", 
					HttpMethod.POST, 
					buildDrugHttpEntity(prescriptionModel),
					ResponseModel.class);
		} catch (RestClientException e) {
			if(attempts < allowedNumberOfRetries) {
				responseFromDrugValidation = validateDrugData(prescriptionModel, attempts++);
			}
		}
		return responseFromDrugValidation;
	}
	
	private ResponseEntity<ResponseModel> validatePatientData(PrescriptionModel prescriptionModel, int attempts) {
		ResponseEntity<ResponseModel> responseFromPatientValidation = null;
		try {
			responseFromPatientValidation = restTemplate.
			exchange(environmentService.getDomainUrl()+"/patients/validate", 
					HttpMethod.POST, 
					buildPatientHttpEntity(prescriptionModel),
					ResponseModel.class);
		} catch (RestClientException e) {
			if(attempts < allowedNumberOfRetries) {
				responseFromPatientValidation = validatePatientData(prescriptionModel, attempts++);
			}
		}
		return responseFromPatientValidation;
	}
	
	public ResponseModel save(PrescriptionModel prescriptionModel) {
		Prescription prescription = new Prescription();
		prescription.setId(prescriptionModel.getId());
		prescription.setDrugid(prescriptionModel.getDrug().getId());
		prescription.setPatientid(prescriptionModel.getPatient().getPid());
		
		ResponseEntity<ResponseModel> responseFromPatientValidation = 
				validatePatientData(prescriptionModel, 0);
		
		if(responseFromPatientValidation == null) {
			showSystemError();
		} else if(ApplicationConstants.PATIENT_VALID_CODE
				.equalsIgnoreCase(responseFromPatientValidation.getBody().getCode())) {
			
			ResponseEntity<ResponseModel> responseFromDrugValidation = 
					validateDrugData(prescriptionModel,0);
			
			if(responseFromDrugValidation == null) {
				showSystemError();
			} else if(ApplicationConstants.DRUG_VALID_CODE
					.equalsIgnoreCase(responseFromDrugValidation.getBody().getCode())) {
				// prescription.setRefid(UUID.randomUUID().toString());
				prescription.setRefid(new Long(System.currentTimeMillis()).toString());
				Prescription prescriptionSaved = prescriptionRepository.save(prescription);
				return new ResponseModel(
						ApplicationConstants.PRESCRIPTION_SAVED_CODE,
						String.format(ApplicationConstants.PRESCRIPTION_SAVED_MESSAGE
								, prescriptionSaved.getRefid()));
			}
			return responseFromDrugValidation.getBody();
		} 
		return responseFromPatientValidation.getBody();
	}
	
	public PrescriptionModel retrievePrescription(String referenceId) {
		PrescriptionModel model = new PrescriptionModel();
		Prescription prescription = prescriptionRepository.findByRefid(referenceId);
		if(prescription != null) {
			model.setId(prescription.getId());
		}
		return model;
	}
	
	public PrescriptionModel retrievePrescriptionDetails(String id) {
		PrescriptionModel model = new PrescriptionModel();
		Optional<Prescription> o_prescription = prescriptionRepository.findById(id);
		if(o_prescription.isPresent()) {
			Prescription prescription = o_prescription.get();
			model.setId(prescription.getId());
			
			ResponseEntity<Patient> responseFromPatientService = null;
			try {
				responseFromPatientService = restTemplate.
						exchange(environmentService.getDomainUrl()+"/patients/id/{id}", 
								HttpMethod.GET, 
								buildEntityWithAuth(),
								Patient.class,
								prescription.getPatientid());
			} catch (RestClientException e) {
				// return showSystemError();
			}
			if(responseFromPatientService != null && responseFromPatientService.getBody() != null) {
				Patient patientData = responseFromPatientService.getBody();
				model.setPatient(patientData);
			}
			
			ResponseEntity<Drug> responseFromDrugService = null;
			try {
				responseFromDrugService = restTemplate.
						exchange(environmentService.getDomainUrl()+"/drugs/id/{id}", 
								HttpMethod.GET, 
								buildEntityWithAuth(),
								Drug.class,
								prescription.getDrugid());
			} catch (RestClientException e) {
				// return showSystemError();
			}
			if(responseFromDrugService != null && responseFromDrugService.getBody() != null) {
				Drug drugData = responseFromDrugService.getBody();
				model.setDrug(drugData);
			}
		}
		return model;
	}
}
