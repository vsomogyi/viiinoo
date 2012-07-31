package com.alasdoo.repositories;

import java.util.List;
import java.util.Map;

import com.alasdoo.entity.Wine;
import com.alasdoo.entity.Winery;
import com.alasdoo.service.CommonsService.OrderBy;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;

/**
 * Interface for Wine and Winery persistence.  
 * 
 */
public interface WineRepository {
	
	// WINE	
	public Wine saveWine(Wine wine);	
	public List<Wine> getAllWines();
	public Wine getWineById(Key id);
	public Key getWineKey(Wine wine);
	public void deleteWine(Key wineId);
	public Wine getWine(String url_name);
	public List<Wine> getWinesByWinery(Winery winery);
	public List<Long> getWinePicture(String url_name);
	public String getWineStyle(Wine wine);
	public boolean wineUrlInUse(String urlName);		
	public String getWineStyle(Entity entity);
	
	//WINERY
	public Winery getWinery(String url_name);
	public void saveWinery(Winery winery);
	public List<Winery> getAllWineries();
	public Winery getWineryByName(String wineryName);
	public Winery getWineryByKey(Key key);
	public List<Long> getWineryPicture(String url_name);
	public Map<Key, Winery> getWineriesByWines(List<Wine> winList);
	// Winery list db pagination with GAE cursor
	public QueryResultList<Entity> getPartialWineriesList(String startCursor,int pageSize,Query.SortDirection sortDirection);
	// Wine list db pagination	with GAE cursor
	public QueryResultList<Entity> getPartialWinesList(String startCursor,int pageSize,Query.SortDirection sortDirection);
	public QueryResultList<Entity> getPartialWinesListByReviewDate(String startCursor,int pageSize,Query.SortDirection sortDirection);
	public List<Wine> getPartialDiaryWinesListByReviewDate(String startcursor ,int pageSize,Key userKey);
	public QueryResultList<Entity> getList(String startCursor, int pageSize,Query.SortDirection sortDirection,OrderBy orderBy);
		
	// VALIDATION
	public boolean wineryNameInUse(String wineryName);	
	public boolean wineNameInUse(String wineName);		
	
	// CHARACTER
	public Key getCharacterKey(com.alasdoo.entity.Character character);
	public Key saveCharacter(com.alasdoo.entity.Character character);
	public com.alasdoo.entity.Character getCharacterByKey(Key key);
	
	

}
