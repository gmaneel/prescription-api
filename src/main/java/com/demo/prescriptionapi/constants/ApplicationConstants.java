package com.demo.prescriptionapi.constants;

public class ApplicationConstants {
	
	public static String DRUG_VALID_CODE = "0000";
	public static String DRUG_VALID_MESSAGE = "Drug Is Valid";
	public static String DRUG_INVALID_CODE = "0001";
	public static String DRUG_INVALID_MESSAGE = "Drug Not Valid";
	
	public static String PATIENT_VALID_CODE = "0000";
	public static String PATIENT_VALID_MESSAGE = "Patient Is Valid";
	public static String PATIENT_INVALID_CODE = "0002";
	public static String PATIENT_INVALID_MESSAGE = "Membership Not Valid";
	
	public static String PRESCRIPTION_SAVED_CODE = "0000";
	public static String PRESCRIPTION_SAVED_MESSAGE = 
			"Prescription Accepted. Your Reference ID is %s. We will get back to you soon.";
	
	public static String SYSTEM_ERROR_CODE = "0003";
	public static String SYSTEM_ERROR_MESSAGE = 
			"We are unable to complete the transaction at this time. Please come back later!";
	

}
