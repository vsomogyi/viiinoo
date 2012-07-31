package com.alasdoo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alasdoo.service.CommonsService.OrderBy;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;

/**
 * This part of code does the buffering for the cursor 
 * @author turzait
 *
 */


public class ListingWrapper {
	//TODO:
	// there is a bug in this code,somewhere...
	private QueryResultList<Entity> bufferList;
	private List<String> cursors = new ArrayList();
	private String currentCursor = null;
	private OrderBy order;
	private Query.SortDirection direction;
	
	
	public ListingWrapper(QueryResultList<Entity> list,OrderBy order,Query.SortDirection direction){
		bufferList = list;
		currentCursor = getCursorsFromData(list);
		this.order = order;
		this.direction = direction;
	}
	
	public QueryResultList<Entity> processList(QueryResultList<Entity> list,int pageSize){
		QueryResultList<Entity> tempList = bufferList;		
		bufferList = list;		
		return tempList;
	}
	
	public String getCursor(){
		return currentCursor;
	}
	
	public OrderBy getOrder() {
		return order;
	}

	public void setOrder(OrderBy order) {
		this.order = order;
	}

	public Map<String,String> prepareCursors(){
	Map<String,String> map = new HashMap();
	String currentCursor = getCursor();
		int index = cursors.indexOf(currentCursor) ;
		if (!cursors.isEmpty()){
			if (index > 0){
				if (index == 1)
					map.put("cursorPrevious", "null");			
				else
					map.put("cursorPrevious", cursors.get(index - 2));
				// set prev
			}
		//	if (cursors.size() - 1 > index)
		if (!bufferList.isEmpty())
				map.put("cursorNext", currentCursor);
		}
		return map;
	}
	
	private String getCursorsFromData(QueryResultList dataList){
		String nextCursor = null;
		if (dataList != null & dataList.size() > 0){			
			nextCursor = dataList.getCursor().toWebSafeString();		
			if (nextCursor != null){				
				if (!cursors.contains(nextCursor)){
					cursors.add(nextCursor);
				}
			}
		}
		// returns the current cursor
		return nextCursor;
	}

	public QueryResultList<Entity> getBufferList() {
		return bufferList;
	}

	public void setBufferList(QueryResultList<Entity> bufferList) {
		currentCursor = getCursorsFromData(bufferList);		
		this.bufferList = bufferList;
	}

}
