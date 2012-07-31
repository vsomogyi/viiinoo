package com.alasdoo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compass.core.engine.SearchEngineQueryParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alasdoo.entity.EMessageType;
import com.alasdoo.entity.MyMessage;
import com.alasdoo.entity.Picture;
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineForm;
import com.alasdoo.entity.Winery;
import com.alasdoo.exceptions.NoRightsException;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.PictureService;
import com.alasdoo.service.SearchService;
import com.alasdoo.service.WineService;
import com.alasdoo.service.view.ViewService;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * This is the main controller of the application.
 * Every request is first processed by this class.
 *  
 * @author Vinofil
 */

@Controller
public class VinofilController extends BaseController implements MessageSourceAware, HandlerExceptionResolver {
	//

	private static final Logger log = Logger.getLogger(VinofilController.class.getName());
	
	@Autowired
	private PictureService pictureService;
	
	@Autowired
	private SearchService searchService;
	
	/**
	 * If any none defined url is asked for, just redirect it to the index
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping("/*")
	public String anyUrl(Model model) {
		return index(model);
	}


	/**
	 * Basic constructor used by Spring
	 */
	public VinofilController() {
		super();
	}


	/**
	 * ROOT - The root of the site
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index (Model model) {
		model.addAttribute("loginURL", userService.createLoginURL("/diary"));	// after login go to the diary		
		model.addAttribute("logoutURL", userService.createLogoutURL("/"));		// after logout go to the first page

		return "index";
	}


	/**
	 * SEARCH -  searches for the query defined in the request
	 * Search in:
	 * - Wine: name, vintage
	 * - Winery:
	 * Throws a runtime excpetion if the query is not parsable.
	 * @param model
	 * @param searchString - search query (based on Lucene)
	 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/search")
	public String search (@RequestParam(value="searchString", required=false) String searchString,Model model) {


		// List<Winery> searchResults = searchJanitor.searchGuestBookEntries(searchString);
		if (searchString != null && !searchString.isEmpty()){
		Map<String,Object> results = searchService.search(searchString);
		
		List<Winery> listWinery = new ArrayList<Winery>();
		List<Wine> listWine = new ArrayList<Wine>();	

		for (Winery winery : (List<Winery>)results.get("Wineries")){			
			listWinery.add(wineRepository.getWineryByKey(winery.getKey()));
		}
		for (Wine wine : (List<Wine>)results.get("Wines")){			
			listWine.add(wineRepository.getWineById(wine.getKey()));
		}
		model.addAttribute("didYouMean", results.get("didYouMean"));
		
		model.addAttribute("hitsNum", results.get("hitsNum"));
		viewService.prepareWineryList(model,listWinery );
		viewService.prepareWineList(model, listWine);
		}
		return "searchResults";
	}



	/**
	 * Picture serving system(GET)
	 * Serves pictures by picture id
	 * @param picId - picture Id 
	 * @param req - HTTP Request
	 * @param res - HTTP Response
	 * @throws IOException
	 */
	@RequestMapping(value = "/pictures/{picId}",method = RequestMethod.GET)
	public void servePicture(@PathVariable String picId,
			HttpServletRequest req, HttpServletResponse res) throws IOException{
		if ((!WineService.EMPTY_STRING.equals(picId)) && (!picId.equals("-1"))) {

			Picture p = pictureService.get(Long.parseLong(picId));
			if (p != null) {
				res.setContentType("image/jpeg");
				res.setHeader("Cache-Control", "max-age=1209600"); //Cache for two weeks
				res.getOutputStream().write(p.getImage().getBytes());  				
			}   
		}
	}


	private MessageSource messageSource;


	@Autowired
	private AccountController accountController;

	@Autowired
	private WineController wineController;

	@Autowired
	private WineryController wineryController;

	/**
	 * 
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;


	}

	/**
	 * GLOBAL RUNTIME EXCEPTION handler
	 * Handles every unexcpeted runtime exception with a general error page,
	 * and specific runtime excpetions with spedific pages. 
	 * Specific Excpetions handled:
	 * MaxUploadSizeExceededException - if the request holds a larger than 1MB picture
	 * SearchEngineQueryParseException - the  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		ExtendedModelMap model = new ExtendedModelMap();
		// This line is for development
		// If there are too much specific exceptions, we should consider putting everything
		//  linked with exceptions in another class 
		log.severe("Handled runtime excpetion, stacktrace:" + ex.getMessage());
		ex.printStackTrace();
		if ( ex instanceof MaxUploadSizeExceededException)
		{

			String rUri =  request.getRequestURI();
			//	Map data = request.getParameterMap();
			model.put ("pictureError", messageSource.getMessage("picture.tooBig",null, Locale.ENGLISH));

			if (rUri.equals("/winery")){
				Winery winery = new Winery();
				model.put("winery", winery);
				return new ModelAndView(wineryController.createNewWineryForm(winery,model),model);	

			} else if (rUri.equals("/wine")){
				WineForm wineForm= new WineForm();
				model.put("wineForm",wineForm);
				return new ModelAndView(wineController.getWineForm(wineForm,model),model);	

			} else if (rUri.equals("/account")){						
				return new ModelAndView(accountController.getAccount(model, ""),model);	
			}
		}
		if (ex instanceof SearchEngineQueryParseException){
			System.out.println("ParseException");
			model.addAttribute("message",new MyMessage(EMessageType.ERROR,messageSource.getMessage(
					"search.parseError",null, Locale.ROOT)));
			return new ModelAndView("searchResults",model);
		}
		if (ex instanceof NoRightsException){
			System.out.println("NoRightsException");
				try {
					accountController.logout(request, response);
					response.sendRedirect(UserServiceFactory.getUserService().createLogoutURL("/"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
		}
		// return main error page
		return new ModelAndView("error", (Map) model);

	}
	

}
