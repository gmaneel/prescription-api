package com.demo.prescriptionapi.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import com.demo.prescriptionapi.entity.Drug;
import com.demo.prescriptionapi.repo.DrugRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class DrugServiceTest {
	
	@InjectMocks
	DrugService drugService;
	
	@Mock
	private DrugRepository drugRepository;
	
	@BeforeAll
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testRetrieve() {
		Optional<Drug> o_expected = Optional.of(new Drug("DR1","Aspirin","DRASP102"));
		when(drugRepository.findById(Mockito.anyString())).thenReturn(o_expected);
		Drug actual = drugService.retrieve("DR1");
		Assert.notNull(actual);
		Assert.isTrue(actual.getId().equalsIgnoreCase(o_expected.get().getId()));
	}
	
	@Test
	public void testValidate() {
		Drug input = new Drug("DR1","Aspirin","DRASP102");
		when(drugRepository.findByIdAndName("DR1","Aspirin")).thenReturn(input);
		Drug actual = drugService.validate(input);
		Assert.notNull(actual);
	}

}
