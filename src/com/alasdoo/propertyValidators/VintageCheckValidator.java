package com.alasdoo.propertyValidators;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VintageCheckValidator implements ConstraintValidator<VintageCheck, Integer> {

	@Override
	public void initialize(VintageCheck arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Integer arg0, ConstraintValidatorContext constraintContext) {
		boolean isValid = false;
		if (arg0 == null)
			isValid = true;
		else{
			Calendar cal =  Calendar.getInstance();
			if (arg0 < cal.get(Calendar.YEAR))
				isValid =  true;
			
		}
		if (!isValid){
			//constraintContext.disableDefaultConstraintViolation();
			constraintContext.buildConstraintViolationWithTemplate( "{com.alasdoo.propertyValidators.VintageCheck.message}");
		}
		return isValid;
	}

}
