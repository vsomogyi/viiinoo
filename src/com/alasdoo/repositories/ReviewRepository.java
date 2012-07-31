package com.alasdoo.repositories;

import java.util.List;

import com.alasdoo.entity.Review;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.exceptions.NoRightsException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.QueryResultList;

/**
 * 
 * @author Vinofil
 *
 */
public interface ReviewRepository {

	/**
	 * Saves the review.
	 */
	public void save(Review review, WineEnthusiast user) throws NoRightsException;	
			
	/**
	 * Gets every review  saved by a user
	 */
	public List<Review> getReviewsByUser (WineEnthusiast we);
	
	/**
	 * Gets every review.
	 */
	public List<Review> getAll();

	/**
	 * Get a review by id
	 */
	public Review getReview(Key id);

	/**
	 * Delete review by id
	 */
	public void deleteReview(Key id) throws NoRightsException;
	
	public List<Review> getReviewsByWineKey(Key key);
	public QueryResultList<Entity> getDiaryList(String startCursor,int pageSize,Key userKey);
	public QueryResultList<Entity> getReviewsList(String startCursor,int pageSize);

	List<Review> getReviewsByWineKey(Key key, Integer limit);
				
}
