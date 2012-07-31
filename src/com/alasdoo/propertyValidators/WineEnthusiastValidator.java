package com.alasdoo.propertyValidators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.alasdoo.entity.WineEnthusiast;

/**
 * Validator class for WineEnthusiast object
 * @author Vinofil
 *
 */
	@Service
	public class WineEnthusiastValidator implements Validator{
			
//		private WineEnthusiastRepository wineEnthusiastRepository;
//		
//		@Required
//		@Autowired
//		public void setwineEnthusiastRepository(WineEnthusiastRepository wineEnthusiastRepository) {
//			this.wineEnthusiastRepository = wineEnthusiastRepository;
//		}

		@Override
		public boolean supports(Class clazz) {
			return WineEnthusiast.class.equals(clazz);
			}

		@Override
		public void validate(Object obj, Errors e) {
		
			ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "wineEnthusiast.name.empty");
			WineEnthusiast we = (WineEnthusiast) obj;
			
			
		}

		
	}



