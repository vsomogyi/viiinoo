package com.alasdoo.service.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alasdoo.service.PictureService;

/**
 * 
 * @author warden
 *
 */
abstract class ViewListBuilder {
	@Autowired
	protected PictureService pictureService;

	@SuppressWarnings("rawtypes")
	protected List objectList;

	@SuppressWarnings("rawtypes")
	public List getObjectList() {
		return objectList;
	}
	
	
	@SuppressWarnings("rawtypes")
	public void createNewObjectList(){
		objectList = new ArrayList();
	}

	public abstract void addDetailsView();
	public abstract void addMainView();
}
