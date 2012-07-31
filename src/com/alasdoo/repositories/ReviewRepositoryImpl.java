package com.alasdoo.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Repository;

import com.alasdoo.entity.Review;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.exceptions.NoRightsException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

@Repository
public class ReviewRepositoryImpl extends JdoDaoSupport implements ReviewRepository{

	@Autowired
	protected PersistenceManagerFactory persistenceManagerFactory;

	//	@Autowired
	//	private WineRepository wineRepository;

	@PostConstruct
	public void init() {

		super.setPersistenceManagerFactory(persistenceManagerFactory);

	}

	public ReviewRepositoryImpl() {
		super();
	}
	/**
	 * Saves the review.
	 */
	@Override
	public void save(Review review,WineEnthusiast user) throws NoRightsException {

		if (review.getDate() == null){	
			review.setDate(new Date());
		}
		if (user != null)
			review.setWineUser(user.getKey());
		PersistenceManager pm = getPersistenceManager(true);

		try {
			pm.makePersistent(review);

		}
		catch (Exception e) {
			//TODO: logging
			e.printStackTrace();
		}
		finally {
			releasePersistenceManager(pm);
		}

	}



	/**
	 * Gets every review.
	 */
	@SuppressWarnings("unchecked")
	public List<Review> getAll() {

		PersistenceManager pm = getPersistenceManager(true);
		Collection<Review> listRev = null;
		List<Review> detached  = null;
		try{
			String query = "select from "+Review.class.getName();

			listRev = ((Collection<Review>) pm.newQuery(query).execute());
			detached = (List<Review>) pm.detachCopyAll(listRev);
		}catch(Exception e){
			e.printStackTrace();
		}  finally {
			pm.close();
		}

		return detached;
	}
	/**
	 * Get a review by id
	 */
	@Override
	public Review getReview(Key id) {
		PersistenceManager pm = getPersistenceManager(true);
		Review result= null;
		try{
			result  = pm.getObjectById(Review.class, id);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;

	}

	/**
	 * Delete review by id
	 */
	@Override
	public void deleteReview(Key id) throws NoRightsException{
		if (!checkUserRigths()) throw new NoRightsException();
		PersistenceManager pm = getPersistenceManager(true);

		try{
			pm.deletePersistent(pm.getObjectById(Review.class,id));
		}
		catch(Exception e){
			e.printStackTrace();
		} finally{
			releasePersistenceManager(pm);
		}

	}

	/**
	 * Dummy function waiting for implementation
	 * @return
	 */
	//TODO!!!
	public Boolean checkUserRigths(){		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Review> getReviewsByUser(WineEnthusiast we) {
		PersistenceManager pm = getPersistenceManager(true);
		Collection<Review> listRev = null;
		List<Review> detached  = null;
		try{

			javax.jdo.Query query = pm.newQuery(Review.class,
					"wineUser == paramName1 ");
			query.declareParameters("String paramName1");
			listRev = ((Collection<Review>)query.execute(we.getKey()));
			detached = (List<Review>) pm.detachCopyAll(listRev);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return detached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Review> getReviewsByWineKey(Key key) {
		PersistenceManager pm = getPersistenceManager(true);
		Collection<Review> listRev = null;
		List<Review> detached  = null;
		try{

			javax.jdo.Query query = pm.newQuery(Review.class,
					"wineKey == paramName1 ");
			query.declareParameters("String paramName1");
			query.setOrdering("date desc");
			listRev = ((Collection<Review>)query.execute(key));
			detached = (List<Review>) pm.detachCopyAll(listRev);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return detached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Review> getReviewsByWineKey(Key key,Integer limit) {
		PersistenceManager pm = getPersistenceManager(true);
		Collection<Review> listRev = null;
		List<Review> detached  = null;
		try{

			javax.jdo.Query query = pm.newQuery(Review.class,
					"wineKey == paramName1 ");
			query.declareParameters("String paramName1");
			query.setOrdering("date desc");
			query.setRange(0, limit);
			listRev = ((Collection<Review>)query.execute(key));
			detached = (List<Review>) pm.detachCopyAll(listRev);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return detached;
	}
	public QueryResultList<Entity> getReviewsList(String startCursor,int pageSize) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Review");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);

		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);

		return results;
	}

	public QueryResultList<Entity> getDiaryList(String startCursor,int pageSize,Key userKey) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Review");
		q.addFilter("wineUser", Query.FilterOperator.EQUAL, userKey);
		q.addSort("date", SortDirection.DESCENDING);	
		PreparedQuery pq = datastore.prepare(q);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);

		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		 startCursor = results.getCursor().toWebSafeString();

		return results;
	}

}

