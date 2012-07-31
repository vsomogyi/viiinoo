package com.alasdoo.service.view;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ViewListDirector {
	
	private ViewListBuilder listBuilder;
	
	public void setListBuilder(ViewListBuilder listBuilder){
		this.listBuilder = listBuilder;
	}
	@SuppressWarnings("unchecked")
	public List getListObject(){
		return listBuilder.getObjectList();
	}
		
	public void constructShortViewList(){
		listBuilder.createNewObjectList();
		listBuilder.addMainView();
	}
	public void constructDetailedViewList(){
		listBuilder.createNewObjectList();
		listBuilder.addDetailsView();
	}
}
