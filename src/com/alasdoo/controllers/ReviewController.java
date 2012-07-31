package com.alasdoo.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alasdoo.entity.MultiPartFileUpload;
import com.alasdoo.entity.ReviewForm;
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.entity.WineForm;
import com.alasdoo.exceptions.NoRightsException;
import com.alasdoo.propertyValidators.WineFormValidator;
import com.alasdoo.service.CommonsService;
import com.alasdoo.service.CommonsService.OrderBy;
import com.alasdoo.service.EntityAdapter;
import com.alasdoo.service.KeyStringConverter;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

@Controller
public class ReviewController extends BaseController implements MessageSourceAware{

	private static final String DIARY_LISTING_WRAPPER = "diaryListingWrapper";

	//private static final String REVIEW_LISTING_WRAPPER = "reviewListingWrapper";
	
	@Autowired
	private WineFormValidator wineFormValidator;
	
	@Autowired  
	private LocalValidatorFactoryBean validator;  

	public void setValidator(LocalValidatorFactoryBean validator) {  
		this.validator = validator;  
	}  

	@Override
	protected QueryResultList<Entity> getListingFromRepository(String cursor,
			Integer PAGE_SIZE, SortDirection sortDirection, OrderBy orderBy) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();

		return reviewRepository.getDiaryList(cursor,PAGE_SIZE,loggedInUser.getKey());
	}

	@Override
	protected String getListingWrapperName() {
		return DIARY_LISTING_WRAPPER;
	}

	/**
	 * Diary - Lists the reviews owned by the user.
	 * 
	 * First get the wines that the user has rated, then collect the winery name
	 * and the reviews for each wine.
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping("/diary")
	public String diary (Model model, @RequestParam(value="cursor", required=false) String cursor,@RequestParam(value="cursor", required=false) String order) {
		//selected menu item	
		model.addAttribute("selected", "diary");
	
		QueryResultList<Entity> list = doListing(model, cursor, order);

		viewService.prepareReviewListByReviews(model,EntityAdapter.convertEntitiesToReviews(list));
	
		return "diary";
	}
	/**
	 *  REVIEW - open a review to view or edit
	 * @param id - review id
	 * @param url_name - unique wine URL
	 * @param operation - edit,view, or delete_confirmation
	 * @param wineForm - Data form
	 * @param model
	 * @param review - Data form
	 * @return
	 * @throws NoRightsException 
	 */
	@RequestMapping(value="/wine/{url_name}/review/{id}/{operation}", method=RequestMethod.GET)
	public String getReview(@PathVariable String id,
			@PathVariable String url_name,
			@PathVariable String operation,
			@ModelAttribute ("wineForm") WineForm wineForm,
			@ModelAttribute ("reviewForm") ReviewForm reviewForm,
			Model model) throws NoRightsException{
		//selected menu item	
		model.addAttribute("selected", "reviews");
		reviewForm.setReview(reviewRepository.getReview(KeyFactory.stringToKey(id)));
		Wine wine = wineRepository.getWineById(reviewForm.getReview().getWineKey());

		wineService.prepareNewWineForm(model);

		if (wine != null){
			wineForm = wineService.convertWineToWineForm(wine);

			if (operation.equals("edit")){
				viewService.prepareWineView(model, wine);
				reviewForm.setWineUrl(wine.getUrl_name());
				model.addAttribute("reviewForm", reviewForm);
				return "reviewEdit";
			}
			else if (operation.equals("view")){
				viewService.prepareReviewView(model, reviewForm.getReview());
				return "reviewView";
			}
			else if (operation.equals("delete_confirmation")){
				deleteReview(wine, reviewForm, id);
				
				return diary(model,null,null);
			}
			return diary(model,null,null);
		}
		return diary(model,null,null);

	}


	/** 
	 * REVIEW - open an empty review form with wine form	
	 * @param wineForm - Data form
	 * @param model
	 * @param review - Data form
	 * @return
	 */

	@RequestMapping(value="/wine/review", method=RequestMethod.GET)
	public String getEditReview(
			@ModelAttribute ("wineForm") WineForm wineForm,			
			@ModelAttribute ("reviewForm") ReviewForm reviewForm,
			@RequestParam(value="id", required=false) String wineUrl,				
			Model model)
	{
		//selected menu item			
		model.addAttribute("selected", "reviews");

		//prepare the review data
		wineService.prepareNewWineForm( model);
		ArrayList<String> wineNames = (ArrayList<String>) wineService.extractWineNames();
		model.addAttribute("wineNames",wineNames);

		// set the wine combobox if the user already selected a wine
		Wine wine = wineRepository.getWine(wineUrl);
		if (wine !=null)
			reviewForm.setWineName(wine.getName());

		return "review";
	}
	@InitBinder
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Key.class, "review.key", new KeyStringConverter());
	}

	/**
	 * REVIEW - Saves a new review
	 * @param wineForm - Data form
	 * @param result - Binding result for WineForm
	 * @param review - Data form
	 * @param result2 Binding result for review
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/wine/review", method=RequestMethod.POST)
	public String postReview(MultiPartFileUpload upload,
			@ModelAttribute ("wineForm") WineForm wineForm,
			BindingResult result,
			@Valid @ModelAttribute ("reviewForm") ReviewForm reviewForm,		
			BindingResult result2,	
			Model model,
			HttpServletRequest request
	) throws NoRightsException{
		boolean hasError  = false;
		boolean oldWine = true;
		if (reviewForm.getWineUrl()==null)
			if (!wineRepository.wineNameInUse(reviewForm.getWineName())){
				wineFormValidator.validate(wineForm, result);
				validator.validate(wineForm, result);  
				hasError = result.hasErrors();  
				oldWine = false;
			}
		if (result.hasErrors())
			model.addAttribute("selected", "reviews");

		if (hasError || result2.hasErrors()){
			return getEditReview(wineForm,reviewForm,"", model);
		}else{
			//	try{
			// authenticated user		 
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();

			Wine wine = null;

			if(oldWine){
				if (reviewForm.getWineUrl()!= null && !"".equals(reviewForm.getWineUrl()))
					wine = wineRepository.getWine(reviewForm.getWineUrl());
				if (wine == null && reviewForm.getWineName() != null && !"".equals(reviewForm.getWineName()))
					wine = wineRepository.getWine(CommonsService.nameToFancyURL(reviewForm.getWineName()));			

			}else{
				wine = wineService.makeWineFromWineForm(wineForm);								 
				pictureService.uploadPicture(upload, request, wine);			
			}
			if (wine !=null){
				if (reviewForm.getReview().getKey() == null)
					wineService.addNewRating(wine, reviewForm.getReview().getScore());
				else
					wineService.updateRating(wine,reviewRepository.getReview(reviewForm.getReview().getKey()).getScore(), reviewForm.getReview().getScore());

				wine = wineRepository.saveWine(wine);
				// we need to get the previous user connection from the db, 
				// and not from the form to be sure that the data is not changed by the user
				if (reviewForm.getReview().getKey()!=null)
					reviewForm.getReview().setWineUser(reviewRepository.getReview(reviewForm.getReview().getKey()).getWineUser());
				reviewForm.getReview().setWineKey(wine.getKey());
				reviewRepository.save(reviewForm.getReview(),loggedInUser);
			}

			//			}catch(Exception ex){
			//				ex.printStackTrace();
			//				return "review";
			//			}
			return diary(model,null,null);
		}


	}

	/**
	 *  REVIEW - This function saves changes or deletes the review based upon client request.
	 * @param id - review id
	 * @param url_name - Unique wine URL
	 * @param operation - edit,view or delete confirmation
	 * @param wineForm - Data form
	 * @param result - Binding result for WineForm
	 * @param model
	 * @param review - Data form
	 * @param result2 - Binding result for Review
	 * @return
	 * @throws NoRightsException 
	 */

	@RequestMapping(value="/wine/{url_name}/review/{id}/{operation}", method=RequestMethod.POST)
	public String processReview(MultiPartFileUpload upload,
			@PathVariable String id,
			@PathVariable String url_name,
			@PathVariable String operation,	
			@ModelAttribute ("wineForm") WineForm wineForm,
			BindingResult result,
			@ModelAttribute ("reviewForm") ReviewForm reviewForm,		    
			BindingResult result2,Model model,
			HttpServletRequest request) throws NoRightsException{

		//selected menu item
		model.addAttribute("selected", "reviews");

		Wine wine = wineForm.getWine();

		if (operation.equals("edit"))					
		{
			// remove the previous score from the average score
			wineService.removeRating(wine, reviewForm.getReview().getScore());
			wineRepository.saveWine(wine);
			reviewForm.getReview().setKey(KeyFactory.stringToKey(id));
			return postReview(upload,wineForm,result,reviewForm, result2,model,request);
		}
		else if (operation.equals("delete_confirmation")){						
			// remove the previous score from the average score
			deleteReview(wine, reviewForm, id);
			
		}
		return diary(model,null,null);
	}

	/**
	 * REVIEW - View selected review
	 * @param id - review id
	 * @param url_name - unique wine URL
	 * @param operation - edit,view, or delete_confirmation
	 * @param wineForm - Data form
	 * @param model
	 * @param review - Data form
	 * @return
	 * @throws NoRightsException 
	 */
	@RequestMapping(value="/wine/{url_name}/review/{id}", method=RequestMethod.GET)
	public String getReviewView(@PathVariable String id,
			@PathVariable String url_name,
			@ModelAttribute ("wineForm") WineForm wineForm,
			@ModelAttribute ("reviewForm") ReviewForm reviewForm, 
			Model model) throws NoRightsException{
		return getReview(id,url_name,"view",wineForm,reviewForm,model); 
	}	
	
	public void deleteReview(Wine wine, ReviewForm reviewForm,String reviewId ) throws NoRightsException{
		wineService.removeRating(wine, reviewForm.getReview().getScore());
		wineRepository.saveWine(wine);
		reviewRepository.deleteReview(KeyFactory.stringToKey(reviewId));

	}
}
