package com.alasdoo.propertyValidators;

import java.util.Set;

import javax.validation.ConstraintViolation;

import junit.framework.Assert;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.alasdoo.entity.WineEnthusiast;

public class AnnotationValidatorTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	 
    @Before
    public void setup() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
    @Test
    public void testSpecialAnnoatationSpecialCharCausesValidationError() {
        WineEnthusiast we = new WineEnthusiast();
        we.setName("!");
        Set<ConstraintViolation<WineEnthusiast>> constraintViolations = localValidatorFactory.validate(we);
        Assert.assertTrue("Expected validation error not found", constraintViolations.size() == 1);
        
    }
    
    @Test
    public void testSpecialAnnotationTextOk() {
        WineEnthusiast we = new WineEnthusiast();
        we.setName("mesterlovesz123456 ?:789.'-_,");
        Set<ConstraintViolation<WineEnthusiast>> constraintViolations = localValidatorFactory.validate(we);
        Assert.assertTrue("Unexpected validation error", constraintViolations.size() == 0);
        
    }
    
    
}
