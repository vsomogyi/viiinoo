package com.alasdoo.propertyValidators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.alasdoo.entity.Winery;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.CommonsService;

/**
 * Validator class for Winery object
 * Contains every manually written validation function for the object.
 * eg.:  need data from db 
 * @author Vinofil
 *
 */
@Service
public class WineryValidator implements Validator{

	private WineRepository wineRepository;

	@Required
	@Autowired
	public void setwineryRepository(WineRepository wineRepository) {
		this.wineRepository = wineRepository;
	}

	@Override
	public boolean supports(Class clazz) {
		return Winery.class.equals(clazz);
	}

	/**
	 * Validates the winery 
	 */
	@Override
	public void validate(Object obj, Errors e) {
	
		Winery winery = (Winery) obj;
		if (winery.getName() != null && !winery.getName().equals(""))
			if (wineRepository.wineryNameInUse(winery.getName())==true){
				if (winery.getUrl_name()!=null && winery.getUrl_name().equals(CommonsService.nameToFancyURL(winery.getName()))){
					//update, data valid 
				}else{
					e.rejectValue( "name", "winery.name.exists");
				}
			}else{
				//update or new, data valid
			}		
	}

}



