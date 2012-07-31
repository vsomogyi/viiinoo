package com.alasdoo.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alasdoo.entity.Character;
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.Winery;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.CommonsService.OrderBy;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

public class MockWineRepositoryTest implements WineRepository{

	Winery winery;
	List<Wine> wineData = new ArrayList<Wine>();
	List<Winery> wineryData = new ArrayList<Winery>();
	public MockWineRepositoryTest(){
		winery = new Winery();
		winery.setName("Winery");
		Winery winery1 = new Winery();
		winery1.setName("Winery1");
		Winery winery2 = new Winery();
		winery2.setName("Winery2");
		wineryData.add(winery1);
		wineryData.add(winery2);
		
		Wine wine1 = new Wine();
		wine1.setName("Wine1");
		Wine wine2 = new Wine();
		wine2.setName("Wine2");
		Wine wine3 = new Wine();
		wine3.setName("Wine3");
		wineData.add(wine1);
		wineData.add(wine2);
		wineData.add(wine3);
	}
	@Override
	public Wine saveWine(Wine wine) {
		// TODO Auto-generated method stub
		return null;	
	}

	@Override
	public List<Wine> getAllWines() {
		return wineData;
	}

	

	@Override
	public Wine getWine(String url_name) {
		// TODO return test wine
		return null;
	}

	@Override
	public List<Wine> getWinesByWinery(Winery winery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Winery getWinery(String url_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveWinery(Winery winery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Winery> getAllWineries() {
		// TODO Auto-generated method stub
		return wineryData;
	}

	@Override
	public Winery getWineryByName(String wineryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Winery getWineryByKey(Key key) {
		return winery;
	}

	@Override
	public boolean wineryNameInUse(String wineryName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wineNameInUse(String wineName) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Key getCharacterKey(Character character) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Key saveCharacter(Character character) {
		// TODO Auto-generated method stub
		return null;
		
	}
	@Override
	public Character getCharacterByKey(Key key) {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public void updateRating(Wine wine, Integer score) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void removeRating(Wine wine, Integer score) {
//		// TODO Auto-generated method stub
//		
//	}
	@Override
	public List<Long> getWineryPicture(String url_name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<Key, Winery> getWineriesByWines(List<Wine> winList) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Long> getWinePicture(String url_name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getWineStyle(Wine wine) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean wineUrlInUse(String urlName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Wine getWineById(Key id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteWine(Key wineId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getWineStyle(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Key getWineKey(Wine wine) {
		// TODO Auto-generated method stub
		return null;
	}
	 
	@Override
	public List<Wine> getPartialDiaryWinesListByReviewDate(String startcursor,
			int pageSize, Key userKey) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryResultList<Entity> getPartialWinesListByReviewDate(
			String startCursor, int pageSize, SortDirection sortDirection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryResultList<Entity> getPartialWineriesList(String startCursor,
			int pageSize, SortDirection sortDirection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryResultList<Entity> getPartialWinesList(String startCursor,
			int pageSize, SortDirection sortDirection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryResultList<Entity> getList(String startCursor, int pageSize,
			SortDirection sortDirection, OrderBy orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
