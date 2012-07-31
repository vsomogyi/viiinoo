package com.alasdoo.service.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.alasdoo.entity.DeletePictureForm;
import com.alasdoo.entity.Review;
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.entity.Winery;
import com.alasdoo.repositories.ReviewRepository;
import com.alasdoo.repositories.WineEnthusiastRepository;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.EntityAdapter;
import com.alasdoo.service.PictureService;
import com.alasdoo.service.WineService;
import com.google.appengine.api.datastore.Key;

/**
 * Encapsulates the ViewMap creating functions. Makes an easy to use interface for the controller, to list what is necessary
 * @author warden
 *
 */
@Service
public class ViewService {

	@Autowired
	private WineService wineService;

	@Autowired
	private WineRepository wineRepository;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private WineEnthusiastRepository wineEnthusiastRepository;


	ViewMapDirector mapDir = new ViewMapDirector();
	ViewListDirector listDir = new ViewListDirector();

	public void prepareWineryView(Model model,Winery winery){

		WineryViewMapBuilder wineryBuilder = new WineryViewMapBuilder(winery,pictureService);				
		mapDir.setMapBuilder(wineryBuilder);
		mapDir.constructDetailedViewMap();

		model.addAttribute("winery", mapDir.getMapObject());

		WineViewListBuilder wineListBuilder = new WineViewListBuilder(wineRepository.getWinesByWinery(winery),wineRepository,wineService,pictureService);
		listDir.setListBuilder(wineListBuilder);
		listDir.constructShortViewList();		
		prepareReviewListByWines(model,(List<Wine>)listDir.getListObject());
		//
		
		model.addAttribute("wines", listDir.getListObject());
	}
	public void prepareWineryList(Model model,List<Winery> list ){
		// building the view list

		WineryViewListBuilder builder = new WineryViewListBuilder(list,pictureService);
		listDir.setListBuilder(builder);
		listDir.constructDetailedViewList();
		model.addAttribute("wineries", listDir.getListObject());

	}

	public void prepareEditWinery(Model model,Winery winery){
		model.addAttribute("winery", winery);

		// get the picture id list, and convert to picture Url						
		List<String> picturesList =	 pictureService.getPicturesListFromPictureKeys(wineRepository.getWineryPicture(winery.getUrl_name()));
		model.addAttribute("pictureList", picturesList);

		// prepare dForm for delete pictures, and for selecting  
		DeletePictureForm dForm = new DeletePictureForm();				
		if (winery.getProfilePicture() != null)
			dForm.setDefaultPicture(pictureService.getPictureUrl(winery.getProfilePicture()));
		model.addAttribute("deletePictureForm",dForm);
	}

	public void prepareWineView(Model model,Wine wine){

		ReviewListBuilder reviewListBuilder = new ReviewListBuilder(reviewRepository.getReviewsByWineKey(wine.getKey()), wineEnthusiastRepository);
		listDir.setListBuilder(reviewListBuilder);
		listDir.constructShortViewList();

		WineViewMapBuilder wineBuilder = new WineViewMapBuilder(wine,wineRepository,wineService,pictureService);				
		mapDir.setMapBuilder(wineBuilder);
		mapDir.constructDetailedViewMap();
		mapDir.getMapObject().put("reviews", listDir.getListObject());

		model.addAttribute("wine",mapDir.getMapObject());				
	}

	public void prepareWineList(Model model,List<Wine> wines){

		WineViewListBuilder wineListBuilder = new WineViewListBuilder(wines,wineRepository,wineService,pictureService);
		listDir.setListBuilder(wineListBuilder);
		listDir.constructShortViewList();
		model.addAttribute("wines", listDir.getListObject());		

	}

	public void prepareReviewView(Model model, Review review){
		ReviewMapBuilder reviewBuilder = new ReviewMapBuilder(review,wineEnthusiastRepository);				
		mapDir.setMapBuilder(reviewBuilder);
		mapDir.constructDetailedViewMap();
		model.addAttribute("review",mapDir.getMapObject());
		//Map<String,Object> reviewMap = wineService.prepareReview(reviewForm.getReview());

		WineViewMapBuilder wineBuilder = new WineViewMapBuilder(wineRepository.getWineById(review.getWineKey()),wineRepository,wineService,pictureService);				
		mapDir.setMapBuilder(wineBuilder);
		mapDir.constructDetailedViewMap();

		model.addAttribute("wine",mapDir.getMapObject());	

	}

	public void prepareReviewListByReviews(Model model,List<Review> reviews){
		// read the wine keys out from the reviews
		List<Key> winesReviewKeys = new ArrayList<Key>();
		for (Review review : reviews){
			if (!winesReviewKeys.contains(review.getWineKey()))
				winesReviewKeys.add(review.getWineKey());
		}
		// get the wines by the keys
		List<Wine> wines = new ArrayList<Wine>();
		for (Key key : winesReviewKeys){
			wines.add(wineRepository.getWineById(key));
		}

		List<Map<String,Object>> wineReviewList = new ArrayList<Map<String,Object>>();
		for (Wine wine : wines){
			WineViewMapBuilder wineBuilder = new WineViewMapBuilder(wine,wineRepository,wineService,pictureService);				
			mapDir.setMapBuilder(wineBuilder);
			mapDir.constructShortViewMap();
			// select the reviews which are connected with the wine	
			List<Review> tempRevList = new ArrayList<Review>();
			for (Review review : reviews){
				if (review.getWineKey().equals(wine.getKey())){
					tempRevList.add(review);
				}
			}

			ReviewListBuilder wineListBuilder = new ReviewListBuilder(tempRevList,wineEnthusiastRepository);
			listDir.setListBuilder(wineListBuilder);
			listDir.constructShortViewList();
			mapDir.getMapObject().put("reviews", listDir.getListObject());

			wineReviewList.add(mapDir.getMapObject());
		}
		model.addAttribute("reviewItems", wineReviewList);

	}

	public void prepareReviewListByWines(Model model,List<Wine> wines){
	
		List<Map<String,Object>> wineReviewList = new ArrayList<Map<String,Object>>();
		for (Wine wine : wines){
			WineViewMapBuilder wineBuilder = new WineViewMapBuilder(wine,wineRepository,wineService,pictureService);				
			mapDir.setMapBuilder(wineBuilder);
			mapDir.constructShortViewMap();
			// select the reviews which are connected with the wine	
			List<Review> tempRevList = new ArrayList<Review>();

			tempRevList.addAll(reviewRepository.getReviewsByWineKey(wine.getKey(),5));
		

		ReviewListBuilder wineListBuilder = new ReviewListBuilder(tempRevList,wineEnthusiastRepository);
		listDir.setListBuilder(wineListBuilder);
		listDir.constructDetailedViewList();
	
		mapDir.getMapObject().put("reviews", listDir.getListObject());
		
		wineReviewList.add(mapDir.getMapObject());
		}
		model.addAttribute("reviewItems", wineReviewList);
	}



	public void prepareUserInfoView(Model model,WineEnthusiast user){
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put("userName",user.getName());
		userMap.put("userWebsite", user.getWebsite());
		userMap.put("userBio", user.getBio());

		Long pictureKey = wineEnthusiastRepository.getProfilePictureId(user);
		userMap.put("userProfilePicture", pictureService.getPictureUrl(pictureKey));

		//userMap.put("wines", wineService.prepareRevievListView(reviews));
		ReviewListBuilder wineListBuilder = new ReviewListBuilder(reviewRepository.getReviewsByUser(user),wineEnthusiastRepository);
		listDir.setListBuilder(wineListBuilder);
		listDir.constructShortViewList();
		userMap.put("reviews", listDir.getListObject());


		model.addAttribute("user", userMap);
	}

}
