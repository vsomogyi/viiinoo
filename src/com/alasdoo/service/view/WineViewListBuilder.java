package com.alasdoo.service.view;

import java.util.List;

import com.alasdoo.entity.Wine;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.PictureService;
import com.alasdoo.service.WineService;

public class WineViewListBuilder extends ViewListBuilder {

	
	
private PictureService pictureService;
private WineRepository wineRepository;
private WineService wineService;	

	private List<Wine> wines;
	
	public WineViewListBuilder(List<Wine> wines,WineRepository wineRepository,WineService wineService,PictureService pictureService){
		this.wines = wines;
		this.pictureService = pictureService;
		this.wineRepository = wineRepository;
	}
	
	@Override
	public void addDetailsView() {
		// TODO Auto-generated method stub
	}

//	@Override
//	public List<Wine> getObjectList() {
//		return wines;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public void addMainView() {
		ViewMapDirector vd = new ViewMapDirector();		
		
		for (Wine wine : wines){
			vd.setMapBuilder(new WineViewMapBuilder(wine,wineRepository,wineService,pictureService));
			vd.constructShortViewMap();
			objectList.add(vd.getMapObject());
		}
	}

}
