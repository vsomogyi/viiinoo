package com.alasdoo.service.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alasdoo.service.PictureService;

@Component
abstract class ViewMapBuilder {
	
	@Autowired
	protected PictureService pictureService;

	protected Map<String,Object> objectMap;

	public Map<String, Object> getObjectMap() {
		return objectMap;
	}
	
	
	public void createNewObjectMap(){
		objectMap = new HashMap<String,Object>();
	}

	public abstract void addDetailsView();
	public abstract void addMainView();
}
