package com.alasdoo.service.view;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ViewMapDirector {

	private ViewMapBuilder mapBuilder;
	
	public void setMapBuilder(ViewMapBuilder mapBuilder){
		this.mapBuilder = mapBuilder;
	}
	public Map<String,Object> getMapObject(){
		return mapBuilder.getObjectMap();
	}
		
	public void constructShortViewMap(){
		mapBuilder.createNewObjectMap();
		mapBuilder.addMainView();
	}
	public void constructDetailedViewMap(){
		mapBuilder.createNewObjectMap();
		mapBuilder.addMainView();
		mapBuilder.addDetailsView();
	}
}
