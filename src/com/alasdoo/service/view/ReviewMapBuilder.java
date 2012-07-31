package com.alasdoo.service.view;

import com.alasdoo.entity.Review;
import com.alasdoo.repositories.WineEnthusiastRepository;
import com.google.appengine.api.datastore.KeyFactory;

public class ReviewMapBuilder extends ViewMapBuilder {

	Review review;
	private WineEnthusiastRepository wineEnthusiastRepository;
	
	public ReviewMapBuilder(Review review,WineEnthusiastRepository wineEnthusiastRepository){
		this.review = review;
		this.wineEnthusiastRepository = wineEnthusiastRepository;
	}
	
	@Override
	public void addDetailsView() {
		objectMap.put("reviewAromaScore", review.getAromaScore());
		objectMap.put("reviewTasteScore", review.getTasteScore());				
		objectMap.put("reviewColorScore", review.getColorScore());				
		objectMap.put("reviewDate", review.getDate());

	}

	@Override
	public void addMainView() {
		 objectMap.put("reviewKey", KeyFactory.keyToString(review.getKey()));
		 objectMap.put("reviewScore", review.getScore().toString());
		 objectMap.put("reviewText", review.getReviewText());		
		 objectMap.put("reviewUserName", wineEnthusiastRepository.getByKey(review.getWineUser()).getName());
		 objectMap.put("reviewUserKey",KeyFactory.keyToString(review.getWineUser()));
		

	}

}
