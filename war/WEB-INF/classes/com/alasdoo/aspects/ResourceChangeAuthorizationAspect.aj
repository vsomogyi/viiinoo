package com.alasdoo.aspects;

import java.util.logging.Logger;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.alasdoo.exceptions.NoRightsException;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.repositories.CompassSearcher;
import com.alasdoo.security.AppRole;

@Component
@Aspect
public class ResourceChangeAuthorizationAspect {

	private static final Logger log = Logger.getLogger(CompassSearcher.class.getName());
	
	@Before("methodsForAuthoriozation()")
	public void testAuthorization() throws Throwable{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WineEnthusiast loggedInUser = (WineEnthusiast)authentication.getPrincipal();
		if (!loggedInUser.getAuthorities().contains(AppRole.USER))
			throw new NoRightsException(); 
		else
			log.info("For user " + loggedInUser.getName() +" access granted for save functions");
	}
	
	
	 @Pointcut("execution(* com.alasdoo.repositories.*.save*(..))")
	    public void methodsForAuthoriozation(){}
	  
}
