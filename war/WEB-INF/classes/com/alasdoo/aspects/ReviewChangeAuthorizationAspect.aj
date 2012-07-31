package com.alasdoo.aspects;

import java.util.logging.Logger;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alasdoo.entity.Review;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.exceptions.NoRightsException;
import com.alasdoo.repositories.CompassSearcher;

public aspect ReviewChangeAuthorizationAspect {

private static final Logger log = Logger.getLogger(CompassSearcher.class.getName());
	
	@Before("methodsForAuthoriozation() && args(review,user)")
	public void testReviewAuthorization(Review review,WineEnthusiast user) throws NoRightsException{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();
		System.out.println("User key: " +loggedInUser.getKey() +   " Review key: "+ review.getWineUser());
		if (review.getWineUser() == null || loggedInUser.getKey().equals(review.getWineUser()))
			log.info("For user " + loggedInUser.getName() +" access granted to edit/delete review");		
		else
			throw new NoRightsException(); 		
	}
	
	
	 @Pointcut("execution(* com.alasdoo.repositories.ReviewRepository.save*(..)) || execution(* com.alasdoo.repositories.ReviewRepository.delete*(..))")
	    public void methodsForAuthoriozation(){}
}
