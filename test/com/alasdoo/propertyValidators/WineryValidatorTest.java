package com.alasdoo.propertyValidators;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.alasdoo.propertyValidators.WineryValidator;


@RunWith(SpringJUnit4ClassRunner.class)
public class WineryValidatorTest {
	
	@Autowired
	private WineryValidator wineryValidator;

	
	public void testValidate(){
//		Errors e = new Errors;
//			wineryValidator.validate(obj, e)
//		
	}
}
