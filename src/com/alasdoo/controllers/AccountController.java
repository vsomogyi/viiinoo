package com.alasdoo.controllers;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.alasdoo.entity.EMessageType;
import com.alasdoo.entity.MultiPartFileUpload;
import com.alasdoo.entity.MyMessage;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.repositories.WineEnthusiastRepository;
import com.alasdoo.security.AppRole;
import com.alasdoo.security.GaeUserAuthentication;
import com.alasdoo.service.EntityAdapter;
import com.alasdoo.service.PictureService;
import com.alasdoo.service.CommonsService.OrderBy;
import com.alasdoo.service.view.ViewService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class AccountController extends BaseController implements MessageSourceAware{

	private static final String ACCOUNT_LISTING_WRAPPER = "accountListingWrapper";

	/**
	 * Logout
	 * 
	 * @param request - HTTP Request
	 * @param response - HTTP Response
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public void logout (HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getSession(false) != null){
			request.getSession(false).invalidate();
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		response.sendRedirect(UserServiceFactory.getUserService().createLogoutURL("/"));

	}

	/** 
	 * REGISTER - Registration form for the new user
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register")
	public String register (Model model,@RequestParam(value="continue", required=false) String continueUrl) {
		return getAccount(model,continueUrl);
	}


	/** 
	 * ACCOUNT - Open account via URL (GET)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account" , method = RequestMethod.GET)
	public String getAccount (Model model,@RequestParam(value="continue", required=false) String continueUrl) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();

		loggedInUser.setEmail(loggedInUser.getUser().getEmail());
		model.addAttribute("wineEnthusiast", loggedInUser);		
		model.addAttribute("continue", continueUrl);	
		
		/// and some magic here
		if (loggedInUser.getProfilePicture() !=null)
			model.addAttribute("picture", pictureService.getPictureUrl(loggedInUser.getProfilePicture()));
		
		return ACCOUNT_EDIT;
	}

	/**
	 * ACCOUNT - Saves the account (POST)
	 * 
	 * @param wineEnthusiast - Data form
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/account",method = RequestMethod.POST)
	public String postAccount (MultiPartFileUpload upload,
			@Valid @ModelAttribute("wineEnthusiast") WineEnthusiast wineEnthusiast,
			BindingResult result,  Model model,@RequestParam(value="continue", required=false) String continueUrl,
			HttpServletRequest request
	) throws IOException{

		// get authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();

		// sets the email to the Google Account email
		// change this to make email editable
		wineEnthusiast.setEmail(loggedInUser.getUser().getEmail());

		if (result.hasErrors()){
			model.addAttribute("continue", continueUrl);
			return ACCOUNT_EDIT; 
		}else{

			// set security role
			wineEnthusiast.setKey(loggedInUser.getKey());
			wineEnthusiast.setUser(loggedInUser.getUser());			
			wineEnthusiast.setEnabled(true);
			wineEnthusiast.setAuthorities(EnumSet.of(AppRole.USER));
			wineEnthusiast.setProfilePicture(loggedInUser.getProfilePicture());
			wineEnthusiast.setPictures(loggedInUser.getPictures());
			// is a file upload in request

			pictureService.uploadPicture(upload, request, wineEnthusiast);
			if (wineEnthusiast.getPictures()!=null && wineEnthusiast.getPictures().size()>0){
				// FIXME use last picture for profile picture. Somewhat silly, what about other pictures?
				wineEnthusiast.setProfilePicture(wineEnthusiast.getPictures().get(wineEnthusiast.getPictures().size() - 1));
				model.addAttribute("picture", pictureService.getPictureUrl(wineEnthusiast.getProfilePicture()));
			}
			// 	update or create wineEnthusiast(user)		
			wineEnthusiastRepository.save(wineEnthusiast);

			// Update the context with the full authentication
			SecurityContextHolder.getContext().setAuthentication(new GaeUserAuthentication(wineEnthusiast, authentication.getDetails()));
			RequestContextHolder.currentRequestAttributes().setAttribute("username", wineEnthusiast.getName(), RequestAttributes.SCOPE_SESSION);
			model.addAttribute("wineEnthusiast", wineEnthusiast);
		}
		if (continueUrl !=null && !EMPTY_STRING.equals( continueUrl.trim() ) ){
			
			model.addAttribute("message",new MyMessage(EMessageType.SUCCESS,messageSource.getMessage("wineEnthusiast.registered",null, Locale.ROOT))); 
			return "redirect:"+ continueUrl;
		}
		else{
			model.addAttribute("message",new MyMessage(EMessageType.SUCCESS,messageSource.getMessage("wineEnthusiast.updated",null, Locale.ROOT))); 
			return ACCOUNT_EDIT; 
		}

	}

	@RequestMapping(value = "/login")
	public String login () throws IOException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();
		if (loggedInUser.getAuthorities().contains(AppRole.USER))
			return "redirect:diary";
		else
			return "redirect:register";
	}
	/** 
	 * ACCOUNT - Basic user info
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account/{userId}" , method = RequestMethod.GET)
	public String getAccountView (Model model,@PathVariable String userId , @RequestParam(value="cursor", required=false) String cursor,@RequestParam(value="cursor", required=false) String order) {
			
		WineEnthusiast user = wineEnthusiastRepository.getByKey(KeyFactory.stringToKey(userId));
			
		viewService.prepareUserInfoView(model,user);
		QueryResultList<Entity> list = doListing(model, cursor, order);

		viewService.prepareReviewListByReviews(model,EntityAdapter.convertEntitiesToReviews(list));
	
		return ACCOUNT_VIEW;
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
		return ACCOUNT_LISTING_WRAPPER;
	}
}
