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
@Pattern(regexp = UrlCharacter.PATTERN_URL, flags = Pattern.Flag.CASE_INSENSITIVE)
public @interface UrlVal {
    String message() default "{com.alasdoo.propertyValidators.urlval}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

interface UrlCharacter {
    //static final String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
	
    static final String PATTERN_URL ="(^$|(?:(())(www\\.([^/?#\\s]*))|((http(s)?|ftp):)(//([^/?#\\s]*)))([^?#\\s]*)(\\?([^#\\s]*))?(#([^\\s]*))?)";
}

