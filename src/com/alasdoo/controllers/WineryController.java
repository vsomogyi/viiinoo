package com.alasdoo.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alasdoo.entity.DeletePictureForm;
import com.alasdoo.entity.MultiPartFileUpload;
import com.alasdoo.entity.Winery;
import com.alasdoo.propertyValidators.WineryValidator;
import com.alasdoo.service.CommonsService;
import com.alasdoo.service.EntityAdapter;
import com.alasdoo.service.ListingWrapper;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

/**
 * Winery Controller - processing request linked with winery
 *  
 * @author turzait
 */

@Controller
public class WineryController extends BaseController{
	
	private static final String WINERYSLISTING_WRAPPER = "winerylistingWrapper";

	@Autowired
	protected WineryValidator wineryValidator;

	/**
	 * WINERY - List the wineries
	 * @return
	 */
	@RequestMapping("/wineries")
	public String listWineries(Model model,@RequestParam(value="cursor", required=false) String cursor,@RequestParam(value="order", required=false) String order){
		model.addAttribute("selected", "wineries");
			
		QueryResultList<Entity> list = doListing(model,cursor,order);
		viewService.prepareWineryList(model,EntityAdapter.convertEntitiesToWineries(list));
		
		return WINERY_LIST;
	}
	
	
	@Override
	protected String getListingWrapperName() {
		return WINERYSLISTING_WRAPPER;
	}


	@Override
	protected QueryResultList<Entity> getListingFromRepository(String cursor,
			Integer PAGE_SIZE, SortDirection sortDirection,CommonsService.OrderBy orderBy) {
		return wineRepository.getPartialWineriesList(cursor, PAGE_SIZE, SortDirection.DESCENDING);
	}
	
	/**
	 * WINERY - Saves the data from the form
	 * 
	 * @param winery - Data form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/winery",method = RequestMethod.POST)
	public String postNewWinery (MultiPartFileUpload upload,
			@Valid @ModelAttribute("winery") Winery winery,
			BindingResult result,
			@ModelAttribute("deletePictureForm") DeletePictureForm delForm,
			Model model,
			HttpServletRequest request) throws IOException{

		//selected menu item
		model.addAttribute("selected", "wineries");

		wineryValidator.validate(winery, result);

		if (result.hasErrors()){
			return "winery";
		}else{		
			pictureService.uploadPicture(upload, request, winery);		
			pictureService.deleteSelectedPictures(delForm, winery);

			// if not new winery, set the data which not need to be changed

			if (winery.getUrl_name() != null && !winery.getUrl_name().equals("")){

				for (Long key : wineRepository.getWineryPicture(winery.getUrl_name()))
					winery.addPicture(key);
			}

			// this function need the winery form db to determine the default pictures,
			// if no new pic is uploaded
			pictureService.setActiveProfilePicture(delForm,winery);						

			wineRepository.saveWinery(winery);
			return listWineries(model,null,null);
		}		
	}

	/**
	 * WINERY - Creates an empty winery form
	 * @param winery
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/winery",method = RequestMethod.GET)
	public String createNewWineryForm(@ModelAttribute("winery") Winery winery, Model model){
		//selected menu item
		model.addAttribute("selected", "wineries");

		return "winery";
	}

	/**
	 * WINERY - view selected Winery
	 * 
	 * @param url_name - Unique winery URL
	 * @param winery - Data form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/winery/{url_name}", method=RequestMethod.GET)
	public String getEditWineryView(@PathVariable String url_name,
			@ModelAttribute("winery") Winery winery,Model model) {
		return  getEditWinery(url_name,"view",winery,model);
	}

	/**
	 * WINERY - open winery page with all the data of the winery
	 * 
	 * @param url_name - Unique winery URL
	 * @param operation - edit,view
	 * @param winery - Data form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/winery/{url_name}/{operation}", method=RequestMethod.GET)
	public String getEditWinery(@PathVariable String url_name,
			@PathVariable String operation,
			@ModelAttribute("winery") Winery winery,Model model) {

		//selected menu item
		model.addAttribute("selected", "wineries");

		winery = wineRepository.getWinery(url_name);	

		if (winery!=null && winery.getUrl_name() != null && !winery.getUrl_name().equals("")){

			if (operation.equals("edit")){
				viewService.prepareEditWinery(model, winery);
				return "winery";
			}
			else if (operation.equals("view")){	
				viewService.prepareWineryView(model, winery);
				return "wineryView";
			}
			else if (operation.equals("delete_confirmation"))
				return "wineryConfirmation";
			else			
				return listWineries(model,null,null);
		}
		return listWineries(model,null,null);
	}
}
