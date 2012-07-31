package com.alasdoo.service.view;

import java.util.List;

import com.alasdoo.entity.Winery;
import com.alasdoo.service.PictureService;


public class WineryViewListBuilder extends ViewListBuilder{

	private PictureService pictureService;
	
	private List<Winery> wineries;
	
	public WineryViewListBuilder(List<Winery> wineries,PictureService pictureService){
		this.wineries = wineries;
		this.pictureService = pictureService;
	}
	public WineryViewListBuilder(List<Winery> wineries){
		this.wineries = wineries;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void addDetailsView() {
		ViewMapDirector vd = new ViewMapDirector();		
		
		for (Winery winery : wineries){
			vd.setMapBuilder(new WineryViewMapBuilder(winery,pictureService));
			vd.constructDetailedViewMap();
			objectList.add(vd.getMapObject());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addMainView() {
		ViewMapDirector vd = new ViewMapDirector();		
		
		for (Winery winery : wineries){
			vd.setMapBuilder(new WineryViewMapBuilder(winery));
			vd.constructShortViewMap();
			objectList.add(vd.getMapObject());
		}
		
	}

}
