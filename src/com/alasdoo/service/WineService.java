package com.alasdoo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.alasdoo.entity.Aged;
import com.alasdoo.entity.EClosure;
import com.alasdoo.entity.EContainer;
import com.alasdoo.entity.EKind;
import com.alasdoo.entity.EType;
import com.alasdoo.entity.ProductionDetails;
import com.alasdoo.entity.Review;
import com.alasdoo.entity.VarietalComposition;
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineForm;
import com.alasdoo.entity.Winery;
import com.alasdoo.repositories.ReviewRepository;
import com.alasdoo.repositories.WineRepository;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

// TODO: write comments, and make javadoc for function usage
/**
 * Services for winery,wineForm and wine. 
 * @author Vinofil
 *
 */
@Service
public class WineService  {

	public static final String EMPTY_STRING = "";

	@Autowired
	private WineRepository wineRepository;

	//@Autowired
	//private PictureService pictureService;

	@Autowired
	private ReviewRepository reviewRepository;

	//@Autowired
	//private WineEnthusiastRepository wineEnthusiastRepository;

	public WineService(WineRepository wineRepository) {
		this.wineRepository = wineRepository;		
	}
	public WineService(){
	}


	public void setWineRepository(WineRepository wineRepository) {
		this.wineRepository = wineRepository;
	}
	//	public void setPictureService(PictureService pictureService) {
	//		this.pictureService = pictureService;
	//	}
	public void setReviewRepository(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	//	public void setWineEnthusiastRepository(
	//			WineEnthusiastRepository wineEnthusiastRepository) {
	//		this.wineEnthusiastRepository = wineEnthusiastRepository;
	//	}
	/**
	 * Converts a Wine object to a WineForm object (support Class for data binding)
	 * @param wine
	 * @return
	 */

	public  WineForm convertWineToWineForm(Wine wine) {
		WineForm wineForm = new WineForm();
		wineForm.setWineryName(wineRepository.getWineryByKey(wine.getWinery()).getName());
		wineForm.setWine(wine);
		if (wine.getProductionDetails() != null){
			ProductionDetails productionDetails = wine.getProductionDetails();
			wineForm.getWine().setProductionDetails(productionDetails);
			if (productionDetails.getAged() != null && !productionDetails.getAged().isEmpty())
			{
				wineForm.getAgedList().addAll(productionDetails.getAged());
			}

		}
		if (wine.getVarietalComposition()!= null && !wine.getVarietalComposition().isEmpty()){
			wineForm.setCompositionList(wine.getVarietalComposition());
		}

		if (wine.getCharacter() != null && !wine.getCharacter().isEmpty()){
			for (Key key : wine.getCharacter()){
				com.alasdoo.entity.Character character = wineRepository.getCharacterByKey(key);
				if (character.getKind() != null){
					if (character.getKind().equals(EKind.Type))
						wineForm.setType(character.getCharacter());
					else
						wineForm.getCharacterList().add(character);
				}
			}
		}

		return wineForm;
	} 

	/**
	 * Places enumeration on the session for the wineForm
	 * @param model
	 */
	public  void prepareNewWineForm(Model model) {

		// sending enum to session,
		model.addAttribute("containers", Arrays.asList(EContainer.values()));
		model.addAttribute("closures", Arrays.asList(EClosure.values()));
		model.addAttribute("wineryNames", extractWineryNames());

		List<EKind> kind = new ArrayList<EKind>(Arrays.asList(EKind.values()));
		kind.remove(EKind.Type);
		model.addAttribute("characters",kind);

		// type
		model.addAttribute("types", Arrays.asList(EType.values()));

	}
	/**
	 * Gets every winery's name 
	 * @return
	 */
	public List<String> extractWineryNames(){
		List<String> result = new ArrayList<String>();
		List<Winery> list = wineRepository.getAllWineries();
		for (Winery winery : list){
			result.add(winery.getName());
		}
		return result;
	}

	/**
	 * Gets every wine's name 
	 * @return
	 */
	public List<String> extractWineNames(){
		List<String> result = new ArrayList<String>();
		List<Wine> list = wineRepository.getAllWines();
		for (Wine wine : list){
			result.add(wine.getName());
		}
		return result;
	}

	/**
	 * Get the Winery from the WineForm
	 * if new Winery name field is populated on the form, a new Winery will be created.
	 * @param wineForm
	 * @return
	 */
	public Winery makeWineryFromWineForm(WineForm wineForm) {
		Winery winery;
		if (wineForm.getNewWineryName() != null && !wineForm.getNewWineryName().equals(EMPTY_STRING)){
			winery = new Winery();
			winery.setName(wineForm.getNewWineryName());
			wineRepository.saveWinery(winery);

		}else{
			winery = wineRepository.getWineryByName(wineForm.getWineryName());
		}
		return winery;
	}
	/**
	 * 
	 * @param wineForm
	 * @return
	 */
	public Wine makeWineFromWineForm(WineForm wineForm) {
		Wine wine = wineForm.getWine();
		if (wine!=null){
			Winery winery = makeWineryFromWineForm(wineForm);
			for (Aged aged : wineForm.getAgedList()){
				if (!wine.getProductionDetails().getAged().contains(aged))
					wine.getProductionDetails().getAged().add(aged);					
			}

			for (VarietalComposition comp : wineForm.getCompositionList()){
				if (!wine.getVarietalComposition().contains(comp))
					wine.getVarietalComposition().add(comp);
			}


			com.alasdoo.entity.Character character = new com.alasdoo.entity.Character("Type", wineForm.getType());

			wineForm.getCharacterList().add(character);
			for (com.alasdoo.entity.Character charac : wineForm.getCharacterList()){
				// danger there is no constraint for EKind
				if (charac.getCharacter()!=""){
					Key key = wineRepository.saveCharacter(charac);
					wine.getCharacter().add(key);
				}
			}

			// if not new wine, get the data which will not be changed

			if (wineForm.getWine().getUrl_name() != null && !wineForm.getWine().getUrl_name().equals("")){
				Wine oldWine = wineRepository.getWine(wineForm.getWine().getUrl_name());
				wine.setAverageRating(oldWine.getAverageRating());
				wine.setNumberOfReviews(oldWine.getNumberOfReviews());
				for (Long key : wineRepository.getWinePicture(wine.getUrl_name()))
					wine.addPicture(key);

			}

			wine.setWinery(winery.getKey());
		}
		return wine;
	} 
	//	/**
	//	 * Prepares list for wine-list.jsp
	//	 * Usage: just place the function result on the session
	//	 * @param winList
	//	 * @return
	//	 */
	//	 public List<Map<String,String>> prepareWineList(List<Wine> winList){
	//		 List<Map<String,String>> wines = new ArrayList<Map<String,String>>();			
	//			
	//		 for (Wine wine : winList){
	//			 Map<String,String> wineMap = new HashMap<String,String>();
	//			
	//			}
	//		 return wines;
	//	 }

	//	 public Map<String,Object> prepareWine(Wine wine){
	//		
	//	 }
	//	 /**
	//	  * 
	//	  * @param winery
	//	  * @return
	//	  */
	//	 public Map<String, Object> prepareWineryView(Winery winery) {
	//			Map<String,Object> wineryMap = new HashMap<String,Object>();
	//
	//			return wineryMap;
	//		}

	//	 public List<Map<String,Object>> prepareRevievListView(List<Review> reviews){
	//		
	//	 }

	//	 public ArrayList<Map<String,Object>> prepareReviewByWine(Wine wine){
	//		 
	//	   
	//	 }

	//	 public ArrayList<Map<String,Object>> prepareReviewByUser(WineEnthusiast we){
	//		 
	//		    ArrayList<Map<String,Object>> reviewList= new ArrayList<Map<String,Object>>(); 
	//		 		
	//			 List<Review> reviews = reviewRepository.getReviewsByUser(we);
	//			 for (Review review : reviews){
	//			//			Map<String,Object> reviewMap = prepareReview(review);
	//			//			reviewList.add(reviewMap);																	
	//				}	
	//			 return reviewList;
	//		 }



	//	 public Map<String,Object> prepareReview(Review review){
	//		 Map<String,Object> reviewMap = new HashMap<String,Object>();
	//		
	//		return reviewMap;
	//	 }

	/**
	 * Updates the average rating of a wine 
	 */
	public void addNewRating(Wine wine, Integer score) {

		// get all reviews for a wine and calculate the overall score
		// this is not possible as GAE does not support sql aggregate functions like count and sum

		Integer reviewNo = wine.getNumberOfReviews();
		reviewNo++;
		wine.setNumberOfReviews(reviewNo);

		// wine now has additional attribute numberOfReviews so each update is calculated
		// based on the previous value and the number of reviews
		Integer averageRating = (wine.getAverageRating() + score) / wine.getNumberOfReviews();

		wine.setAverageRating(averageRating);
		wine.setReviewLastModified(new Date());
		wine.setHasReviews(true);
	}

	/**
	 * Updates the average rating of a wine 
	 */
	public void updateRating(Wine wine,Integer oldScore, Integer newScore) {


		// wine now has additional attribute numberOfReviews so each update is calculated
		// based on the previous value and the number of reviews
		Integer averageRating = (wine.getAverageRating()*wine.getNumberOfReviews() - oldScore + newScore) / wine.getNumberOfReviews();

		wine.setAverageRating(averageRating);
		wine.setReviewLastModified(new Date());
		wine.setHasReviews(true);
	}

	/**
	 * Removes a rating from the average rating
	 */
	public void removeRating(Wine wine, Integer score) {

		// get all reviews for a wine and calculate the overall score
		// this is not possible as GAE does not support sql aggregate functions like count and sum

		Integer reviewNo = wine.getNumberOfReviews();
		reviewNo--;

		// wine now has additional attribute numberOfReviews so each update is calculated
		// based on the previous value and the number of reviews
		Integer averageRating = 0;
		if (reviewNo > 0){
			averageRating = (wine.getAverageRating()*wine.getNumberOfReviews() - score) / (reviewNo);
		}
		wine.setNumberOfReviews(reviewNo);
		wine.setAverageRating(averageRating);

		// wine.reviewLastModified should be the date of the review before the deleted one
		List<Review> reviews = reviewRepository.getReviewsByWineKey(wine.getKey());
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setLenient(false);
		calendar.set(GregorianCalendar.YEAR, 2000);
		calendar.set(GregorianCalendar.MONTH, 1);
		calendar.set(GregorianCalendar.DATE, 1);
		Date reviewLastModified = calendar.getTime();
		for (Review review : reviews){
			if (review.getDate().after(reviewLastModified))
				reviewLastModified = review.getDate();
		}
		wine.setReviewLastModified(reviewLastModified);
	}
	
	public Query.SortDirection convertStringToSortDirection(String order){
		if (order !=null && order.contains("asc"))
			return Query.SortDirection.ASCENDING;
		else
			return Query.SortDirection.DESCENDING;
				
	}
}
