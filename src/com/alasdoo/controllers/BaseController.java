package com.alasdoo.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.repositories.ReviewRepository;
import com.alasdoo.repositories.WineEnthusiastRepository;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.CommonsService;
import com.alasdoo.service.ListingWrapper;
import com.alasdoo.service.PictureService;
import com.alasdoo.service.WineService;
import com.alasdoo.service.view.ViewService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.users.UserService;

@Controller
abstract public class BaseController implements MessageSourceAware{

	
	protected static String ACCOUNT_VIEW = "view-account";
	protected static String ACCOUNT_EDIT = "edit-account";
	protected static String WINE_VIEW= "view-wine";
	protected static String WINE_LIST= "list-wine";
	protected static String WINE_EDIT = "edit-wine";
	protected static String WINERY_VIEW = "view-winery";
	protected static String WINERY_LIST = "wineries";
	protected static String WINERY_EDIT = "edit-winery";
	// number of elements for pagination
	protected static final int PAGE_SIZE = 5;
	
	@Autowired
	protected UserService userService;

	@Autowired
	protected ViewService viewService;

	@Autowired
	protected WineService wineService;

	@Autowired
	protected PictureService pictureService;
	
	protected static final String EMPTY_STRING = "";

	protected MessageSource messageSource;
	
	@Autowired
	protected WineEnthusiastRepository wineEnthusiastRepository;
	
	@Autowired
	protected WineRepository wineRepository;

	@Autowired
	protected ReviewRepository reviewRepository;

	protected static final Logger log = Logger.getLogger(VinofilController.class.getName());
	/**
	 * 
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	protected ListingWrapper currentListingWrapper(){
		return (ListingWrapper)RequestContextHolder.currentRequestAttributes()
		.getAttribute(getListingWrapperName(), RequestAttributes.SCOPE_SESSION);
	}
	
	@ModelAttribute("getLoggedInUser")
	protected String getLoggedInUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication !=null && authentication.getPrincipal() instanceof  WineEnthusiast){
			WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();
			if (loggedInUser.getKey() != null)
				return KeyFactory.keyToString(loggedInUser.getKey());
		}
			return "";
	}
	
	 protected QueryResultList<Entity> getListingFromRepository(String cursor,Integer PAGE_SIZE,Query.SortDirection sortDirection,CommonsService.OrderBy orderBy){
		 log.severe("Listing function is not redefined");
		return null;
	}
	  
	 protected String getListingWrapperName(){
		 log.severe("Wrapper name is not defined");
			
		 return "";
	 }
	 protected QueryResultList<Entity> doListing(Model model,String cursor,String order){
		 
		 	Query.SortDirection direction = wineService.convertStringToSortDirection(order);
			CommonsService.OrderBy orderBy = CommonsService.convertStringToSortOrder(order);
			
			if (cursor != null && cursor.equals("null"))
				cursor = null;
			ListingWrapper listingWrapper = currentListingWrapper();

			if (listingWrapper == null || (listingWrapper.getOrder()!= null && !listingWrapper.getOrder().equals(orderBy))){
				listingWrapper = new ListingWrapper(getListingFromRepository(cursor,PAGE_SIZE,direction,orderBy),orderBy,direction);			
				}
			else{
				listingWrapper.setBufferList(getListingFromRepository(cursor,PAGE_SIZE,direction,orderBy));
			}
			RequestContextHolder.currentRequestAttributes().setAttribute(getListingWrapperName(), listingWrapper, RequestAttributes.SCOPE_SESSION);
			RequestContextHolder.currentRequestAttributes().setAttribute("order", orderBy, RequestAttributes.SCOPE_REQUEST);
			
			cursor = listingWrapper.getCursor();
			QueryResultList<Entity> list =listingWrapper.processList(getListingFromRepository(cursor,PAGE_SIZE,direction,orderBy),PAGE_SIZE);
			
			model.addAllAttributes(listingWrapper.prepareCursors());
			//model.addAttributes(listingWrapper.prepareCursors());
			
			return list;
		}
}
