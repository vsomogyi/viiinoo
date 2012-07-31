package com.alasdoo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceAware;
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
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineForm;
import com.alasdoo.propertyValidators.WineFormValidator;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.CommonsService;
import com.alasdoo.service.EntityAdapter;
import com.alasdoo.service.ListingWrapper;
import com.alasdoo.service.WineService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

@Controller
public class WineController extends BaseController implements MessageSourceAware{

	private static final String WINESLISTING_WRAPPER = "wineslistingWrapper";

	@Autowired
	private WineService wineService;

	@Autowired
	private WineRepository wineRepository;

	@Autowired
	private WineFormValidator wineFormValidator;

	public ListingWrapper currentListingWrapper(){
		return (ListingWrapper)RequestContextHolder.currentRequestAttributes()
		.getAttribute(WINESLISTING_WRAPPER, RequestAttributes.SCOPE_SESSION);
	}
	/**
	 * WINE - Open edit , insert wine or delete wine page
	 * 
	 * @param wine - Data form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/wine/{url_name}/{operation}" ,method = RequestMethod.GET)
	public String getEditWine( @PathVariable String url_name,
			@PathVariable String operation,
			@ModelAttribute("wineForm") WineForm wineForm, Model model){

		//selected menu item		
		model.addAttribute("selected", "wines");

		Wine wine = wineRepository.getWine(url_name);
		wineService.prepareNewWineForm(model);

		if (wine!= null){

			wineForm = wineService.convertWineToWineForm(wine);

			if (operation.equals("edit")){
				model.addAttribute("wineForm",wineForm);
				if (wine.getUrl_name() != null && !wine.getUrl_name().equals("")){
					List<String> picturesList =	 pictureService.getPicturesListFromPictureKeys(wineRepository.getWinePicture(wine.getUrl_name()));
					model.addAttribute("pictureList", picturesList);

					DeletePictureForm dForm = new DeletePictureForm();
					if (wine.getProfilePicture() != null)
						dForm.setDefaultPicture(pictureService.getPictureUrl(wine.getProfilePicture()));
					model.addAttribute("deletePictureForm",dForm);
				}
				return "wine";
			}
			else if (operation.equals("view")){

				if (wine.getUrl_name() != null && !wine.getUrl_name().equals("")){
					viewService.prepareWineView(model, wine);
				}

				return "wineView";
			}
			else if (operation.equals("delete_confirmation"))
				return "wineConfirmation";
			else			
				return winesList(model,null,null);
		}
		return winesList(model,null,null);
	}

	/**
	 * WINE - View selected wine
	 * 
	 * @param wine - Data form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/wine/{url_name}" ,method = RequestMethod.GET)
	public String getEditWineView( @PathVariable String url_name,
			@ModelAttribute("wineForm") WineForm wineForm, Model model){
		return getEditWine(url_name,"view",wineForm, model);
	}

	@Override
	protected String getListingWrapperName() {
		// TODO Auto-generated method stub
		return WINESLISTING_WRAPPER;
	}
	@Override
	protected QueryResultList<Entity> getListingFromRepository(String cursor,
			Integer PAGE_SIZE, SortDirection sortDirection, CommonsService.OrderBy orderBy) {
		return wineRepository.getList(cursor, PAGE_SIZE, SortDirection.DESCENDING,orderBy);
	}
	/**
	 * WINE - prepares an empty wineForm
	 * @param wineForm - Data form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/wine" ,method = RequestMethod.GET)
	public String getWineForm(
			@ModelAttribute("wineForm") WineForm wineForm, Model model){

		//selected menu item
		model.addAttribute("selected", "wines");

		wineService.prepareNewWineForm(model);
		return "wine";
	}

	/**
	 * WINE - Lists the wines
	 * @return
	 */
	@RequestMapping("/wines")
	public String winesList(Model model,@RequestParam(value="cursor", required=false) String cursor,@RequestParam(value="order", required=false) String order){
	
		//selected menu item 
		model.addAttribute("selected", "wines");

		QueryResultList<Entity> list = doListing(model,cursor,order);
		viewService.prepareReviewListByWines(model,EntityAdapter.convertEntitiesToWines(list));
		
		//viewService.prepareWineList(model,wineRepository.getAllWines());
		return "wines";
	}

	/**
	 * WINE - saves the data from the form if valid
	 * 
	 * @param wineForm - Data form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/wine" ,method = RequestMethod.POST)
	public String postWine(MultiPartFileUpload upload,
			@Valid @ModelAttribute("wineForm") WineForm wineForm, 
			BindingResult result,
			@ModelAttribute("deletePictureForm") DeletePictureForm delForm,
			Model model,HttpServletRequest request) throws IOException{

		//selected menu item
		model.addAttribute("selected", "wines");

		wineFormValidator.validate(wineForm, result);				

		if (result.hasErrors() ){
			return getWineForm(wineForm,model);
		}else{

			Wine wine = wineService.makeWineFromWineForm(wineForm);

			if (wine!=null){

				pictureService.uploadPicture(upload, request, wine);		
				pictureService.deleteSelectedPictures(delForm, wine);
				pictureService.setActiveProfilePicture(delForm,wine);						

				wineRepository.saveWine(wine);
			}
			return "redirect:/wines";
		}
	}	
}
