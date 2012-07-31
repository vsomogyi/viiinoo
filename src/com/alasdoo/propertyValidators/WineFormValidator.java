package com.alasdoo.propertyValidators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.alasdoo.entity.WineForm;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.CommonsService;


/**
 * Validator class for Wineform object
 * @author Vinofil
 *
 */
@Service
public class WineFormValidator implements Validator{

	private WineRepository wineRepository;
	
	@Required
	@Autowired
	public void setWineryRepository(WineRepository wineRepository) {
		this.wineRepository = wineRepository;
	}
	
	@Override
	public boolean supports(Class clazz) {
		return WineForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		WineForm wineForm = (WineForm) obj;
			
		//wine.name must be unique
		if (wineForm.getWine().getName() != null && !wineForm.getWine().getName().equals(""))
			if (wineRepository.wineNameInUse(wineForm.getWine().getName())==true){
				if (wineForm.getWine().getUrl_name()!=null && wineForm.getWine().getUrl_name().equals(CommonsService.nameToFancyURL(wineForm.getWine().getName()))){
					//update, data valid
				}else{
					e.rejectValue( "wine.name", "wine.name.exists");
				}
			}else{
				//update or new, data valid
			}
			
		if (wineForm.getNewWineryName() !=null && !(wineForm.getNewWineryName().equals(""))){
			if (wineRepository.wineryNameInUse(wineForm.getNewWineryName())){
				e.rejectValue( "newWineryName", "winery.name.exists");				
			}
		}else{
			if (!wineRepository.wineryNameInUse(wineForm.getWineryName()))
					e.rejectValue( "wineryName", "winery.name.notExists");				
			
		}
	// TODO ask amountProduced and bootleSize
	}

}
