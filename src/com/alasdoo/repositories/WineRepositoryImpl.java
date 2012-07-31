package com.alasdoo.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.datanucleus.store.appengine.query.JDOCursorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Repository;

import com.alasdoo.controllers.VinofilController;
import com.alasdoo.entity.Character;
import com.alasdoo.entity.EKind;
import com.alasdoo.entity.Review;
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.Winery;
import com.alasdoo.service.CommonsService;
import com.alasdoo.service.CommonsService.OrderBy;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query;


/**
 * Implementation of the Wine repository interface.
 * This class communicates with the persistence layer 
 * Root entities: Wine, Winery
 * @author turzait
 *
 */
@Repository
public class WineRepositoryImpl extends JdoDaoSupport implements WineRepository{


	private static final Logger log = Logger.getLogger(VinofilController.class.getName());
	
	@Autowired
	protected PersistenceManagerFactory persistenceManagerFactory;


	@PostConstruct
	public void init() {

		super.setPersistenceManagerFactory(persistenceManagerFactory);

	}
	public WineRepositoryImpl() {
		super();
	}

	@Override
	public void saveWinery(Winery winery) {
		if (winery.getKey()==null)
			winery.setDate(new Date());
		
		if (winery.getUrl_name() != null && !winery.getUrl_name().equals(""))
			winery.setKey(getWinery(winery.getUrl_name()).getKey());

		winery.setUrl_name(CommonsService.nameToFancyURL(winery.getName()));
		//	winery.prepareIndex();
		PersistenceManager pm = getPersistenceManager(true);

		try {
			pm.makePersistent(winery);
		}
		catch (Exception e) {
			e.printStackTrace();
			//TODO: logging
		}
		finally {
			releasePersistenceManager(pm);
		}
	}




	@Override
	public Wine saveWine(Wine wine) {
		if (wine.getKey() == null)
			wine.setDate(new Date());			

		Wine oldWine = getWine(wine.getUrl_name());
		if (oldWine !=null){
			wine.setKey(oldWine.getKey());
			// some magic here
			wine.getProductionDetails().setId(oldWine.getProductionDetails().getId());
		}
		else{
			}
		wine.setUrl_name(CommonsService.nameToFancyURL(wine.getName()));
		PersistenceManager pm = getPersistenceManager(true);
		try {
			wine = pm.makePersistent(wine);

		}
		catch (Exception e) {
			e.printStackTrace();
			//	System.out.println("Error while saving wine");
			//TODO: logging
		}
		finally {
			releasePersistenceManager(pm);
		}
		return wine;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Wine> getAllWines() {
		PersistenceManager pm = getPersistenceManager(true);
		Collection<Wine> listWine = null;
		List<Wine> detached  = null;

		try{
			String query = "select from "+Wine.class.getName();		
			listWine = (Collection<Wine>) pm.newQuery(query).execute();
			detached = (List<Wine>) pm.detachCopyAll(listWine);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			pm.close();
		}
		// Delete  all instances from the DB
		//				pm.deletePersistentAll(res);
		//				System.out.println("DELETED: Wine");

		return detached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Winery getWinery(String url_name) {
		PersistenceManager pm = getPersistenceManager(true);
		Winery result= null;

		try{
			javax.jdo.Query query = pm.newQuery(Winery.class,
			"url_name == paramName ");
			query.declareParameters("String paramName");
			result = ((List<Winery>)query.execute(url_name)).get(0);

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;
	}


	@Override
	public Wine getWineById(Key wine) {

		PersistenceManager pm = getPersistenceManager(true);
		Wine result= null;
		try{
			result  = pm.getObjectById(Wine.class, wine);
			result.getProductionDetails();
			result.getProductionDetails().getAged();
			result.getVarietalComposition();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;

	}
	@Override
	public Key getWineKey(Wine wine) {
		PersistenceManager pm = getPersistenceManager(true);
		Key key = null;
		try{
			key =  (Key) pm.getObjectId(wine);
		}
		catch(Exception e){
			e.printStackTrace();
		} finally{
			releasePersistenceManager(pm);
		}
		return key;
	}
	@Override
	public void deleteWine(Key wineId) {
		PersistenceManager pm = getPersistenceManager(true);

		try{
			pm.deletePersistent(pm.getObjectById(wineId));
		}
		catch(Exception e){
			e.printStackTrace();
		} finally{
			releasePersistenceManager(pm);
		}

	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Winery> getAllWineries() {
		PersistenceManager pm = getPersistenceManager(true);
		Collection<Winery> listWine = null;
		List<Winery> detached  = null;

		try{
			String query = "select from "+Winery.class.getName();		
			listWine = (Collection<Winery>) pm.newQuery(query).execute();
			detached = (List<Winery>) pm.detachCopyAll(listWine);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			pm.close();
		}

		return detached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Wine getWine(String url_name) {
		PersistenceManager pm = getPersistenceManager(true);
		Wine result= null;

		try{
			javax.jdo.Query query = pm.newQuery(Wine.class,
			"url_name == paramName ");
			query.declareParameters("String paramName");
			List<Wine> lw = (List<Wine>)query.execute(url_name);
			if (!lw.isEmpty()){
				result = (lw).get(0);
				result.getProductionDetails();
				result.getProductionDetails().getAged();
				result.getVarietalComposition();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;
	}

	public boolean wineryNameInUse(String wineryName) {
		PersistenceManager pm = getPersistenceManager(true);

		try{
			javax.jdo.Query query = pm.newQuery(Winery.class,
			"name == paramName ");
			query.declareParameters("String paramName");
			@SuppressWarnings("unchecked")
			List<Winery> res = (List<Winery>) query.execute(wineryName);

			if ((res.size() > 0)) {			
				return true;
			}else
				return false;

		}catch (Exception e) {
			e.printStackTrace();
			return true;

		} finally {
			releasePersistenceManager(pm);
		}
	}
	public boolean wineNameInUse(String wineName) {
		PersistenceManager pm = getPersistenceManager(true);

		try{
			javax.jdo.Query query = pm.newQuery(Wine.class,
			"name == paramName ");
			query.declareParameters("String paramName");
			@SuppressWarnings("unchecked")
			List<Wine> res = (List<Wine>) query.execute(wineName);

			if ((res.size() > 0)) {			
				return true;
			}else
				return false;

		}catch (Exception e) {
			e.printStackTrace();
			return true;

		} finally {
			releasePersistenceManager(pm);
		}

	}

	@SuppressWarnings("unchecked")	
	@Override
	public List<Wine> getWinesByWinery(Winery winery) {
		PersistenceManager pm = getPersistenceManager(true);
		List<Wine> wineList= null;
		List<Wine> detached = null;
		try{
			javax.jdo.Query query = pm.newQuery(Wine.class,
			"winery == paramName ");
			query.declareParameters("String paramName");
			wineList = ((List<Wine>)query.execute(winery.getKey()));
			detached = (List<Wine>) pm.detachCopyAll(wineList);

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return detached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Winery getWineryByName(String wineryName) {
		PersistenceManager pm = getPersistenceManager(true);
		Winery result= null;

		try{
			javax.jdo.Query query = pm.newQuery(Winery.class,
			"name == paramName ");
			query.declareParameters("String paramName");
			result = ((List<Winery>)query.execute(wineryName)).get(0);


		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;
	}
	@Override
	public Winery getWineryByKey(Key key) {
		PersistenceManager pm = getPersistenceManager(true);
		Winery winery = null;
		try{
			winery = (Winery) pm.getObjectById(Winery.class,key);
		}
		catch(Exception e){
			e.printStackTrace();
		} finally{
			releasePersistenceManager(pm);
		}
		return winery;
	}
	@Override
	public Key saveCharacter(com.alasdoo.entity.Character character) {
		PersistenceManager pm = getPersistenceManager(true);

		try {
			character = pm.makePersistent(character);
		}
		catch (Exception e) {
			e.printStackTrace();
			//TODO: logging
		}
		finally {
			releasePersistenceManager(pm);
		}

		return character.getKey();

	}
	@Override
	public com.alasdoo.entity.Character getCharacterByKey(Key key) {

		PersistenceManager pm = getPersistenceManager(true);
		com.alasdoo.entity.Character result= null;
		try{
			result  = pm.getObjectById(com.alasdoo.entity.Character.class, key);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;

	}
	@Override
	public Key getCharacterKey(Character character) {
		PersistenceManager pm = getPersistenceManager(true);
		Key key = null;
		try{
			key = (Key) pm.getObjectId(character);
		}
		catch(Exception e){
			e.printStackTrace();
		} finally{
			releasePersistenceManager(pm);
		}
		return key;
	}
	@Override
	public List<Long> getWineryPicture(String url_name) {
		if (getWinery(url_name).getPictures() == null)
			return new ArrayList<Long>();
		else
			return getWinery(url_name).getPictures();
	}

	@Override
	public List<Long> getWinePicture(String url_name) {
		if (getWine(url_name).getPictures() == null)
			return new ArrayList<Long>();
		else
			return getWine(url_name).getPictures();
	}
	@Override
	public Map<Key, Winery> getWineriesByWines(List<Wine> winList) {
		Map<Key,Winery> result = new HashMap<Key,Winery>();
		for (Wine wine : winList){
			if (!result.containsKey(wine.getWinery()))
				result.put(wine.getWinery(),getWineryByKey(wine.getWinery()));
		}
		return result;
	}

	public String getWineStyle(Wine wine) {

		Character character;

		for (Key key : wine.getCharacter()) {
			character = getCharacterByKey(key);
			try{
				if(character.getKind().equals(EKind.Type)) {
					return character.getCharacter();
				}
			}catch(NullPointerException ex){
				log.log(Level.SEVERE, "Character Kind is null! (Character is not in the state in it should be, possible saving error.)");
				ex.printStackTrace();
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getWineStyle(Entity entity) {

		Character character;

		for (Key key : (List<Key>)entity.getProperty("character")) {
			character = getCharacterByKey(key);

			if(character.getKind().equals(EKind.Type)) {
				return character.getCharacter();
			}
		}

		return null;
	}

	@Override
	public boolean wineUrlInUse(String urlName) {
		PersistenceManager pm = getPersistenceManager(true);

		try{
			javax.jdo.Query query = pm.newQuery(Wine.class,
			"url_name == paramName ");
			query.declareParameters("String paramName");
			@SuppressWarnings("unchecked")
			List<Wine> res = (List<Wine>) query.execute(urlName);

			if ((res.size() > 0)) {			
				return true;
			}else
				return false;

		}catch (Exception e) {
			e.printStackTrace();
			return true;

		} finally {
			releasePersistenceManager(pm);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })

	private QueryResultList<Entity> getPagingList(String startCursor,int pageSize,String type,String sortFilter,SortDirection sortDirection) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(type);
		q.addSort("date", sortDirection);

		PreparedQuery pq = datastore.prepare(q);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);

		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}
		QueryResultList results = pq.asQueryResultList(fetchOptions);
		// startCursor = results.getCursor().toWebSafeString();

		return results;
	}


	public  QueryResultList<Entity> getPartialWineriesList(String startcursor ,int pageSize,Query.SortDirection sortDirection){
		return getPagingList(startcursor ,pageSize,"Winery","date",sortDirection);
	}

	public  QueryResultList<Entity> getPartialWinesList(String startcursor ,int pageSize,Query.SortDirection sortDirection){
		return getPagingList(startcursor ,pageSize,"Wine","date",sortDirection);
	}
	
	public QueryResultList<Entity> getPartialWinesListByReviewDate(String startCursor, int pageSize,Query.SortDirection sortDirection) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Wine");

		q.addSort("reviewLastModified", sortDirection);			
		q.addFilter("hasReviews", Query.FilterOperator.EQUAL, true);

		PreparedQuery pq = datastore.prepare(q);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);

		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);

		return results;

	}
	
	public QueryResultList<Entity> getList(String startCursor, int pageSize,Query.SortDirection sortDirection,OrderBy orderBy){
		if (OrderBy.REVIEWCREATIONDATE.equals(orderBy))
			return getPartialWinesListByReviewDate(startCursor,pageSize,sortDirection);
		else
			return getPartialWinesList(startCursor,pageSize,sortDirection);		
	}
	
	public List<Wine> getPartialDiaryWinesListByReviewDate(String startcursor ,int pageSize,Key userKey){
		javax.jdo.Query subQuery = getPersistenceManager().newQuery(Review.class);
		subQuery.setFilter("wineUser == userId");
		subQuery.declareParameters("Key userId");
		subQuery.setResult("wineId");
		
		javax.jdo.Query q = getPersistenceManager().newQuery(Wine.class);
		q.setFilter("key == wineId");
		q.addSubquery(subQuery , "List subQuery", null,"Key userId");
		q.declareParameters("Key userId");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		List<Wine> friends = (List<Wine>) q.execute(userKey);
	
	///
		
		Cursor cursor = Cursor.fromWebSafeString(startcursor);
        Map<String, Object> extensionMap = new HashMap<String, Object>();
        extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
        q.setExtensions(extensionMap);
        q.setRange(0, pageSize);
		
		///
    	List<Wine> results = (List<Wine>) q.execute(userKey);
		return results;
	}
}
