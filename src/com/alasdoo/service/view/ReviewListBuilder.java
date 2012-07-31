package com.alasdoo.service.view;

import java.util.List;

import com.alasdoo.entity.Review;
import com.alasdoo.repositories.WineEnthusiastRepository;

public class ReviewListBuilder extends ViewListBuilder {

	
private List<Review> reviews;
	
private WineEnthusiastRepository wineEnthusiastRepository;

	public ReviewListBuilder(List<Review> reviews,WineEnthusiastRepository wineEnthusiastRepository){
		this.reviews = reviews;
		this.wineEnthusiastRepository = wineEnthusiastRepository;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void addDetailsView() {
		ViewMapDirector vd = new ViewMapDirector();		
		
		for (Review review : reviews){
			vd.setMapBuilder(new ReviewMapBuilder(review,wineEnthusiastRepository));
			vd.constructDetailedViewMap();
			objectList.add(vd.getMapObject());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addMainView() {
		ViewMapDirector vd = new ViewMapDirector();		
		
		for (Review review : reviews){
			vd.setMapBuilder(new ReviewMapBuilder(review,wineEnthusiastRepository));
			vd.constructShortViewMap();
			objectList.add(vd.getMapObject());
		}

	}
	
}
