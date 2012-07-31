package com.alasdoo.propertyValidators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;


@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = ConstantsCharacter.PATTERN_NOSPECIAL, flags = Pattern.Flag.CASE_INSENSITIVE)
public @interface NotSpecial {
    String message() default "{com.alasdoo.propertyValidators.notspecial}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

interface ConstantsCharacter {
    //static final String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
   
    static final String PATTERN_NOSPECIAL ="[a-z0-9?: '.,_-]*";
}

