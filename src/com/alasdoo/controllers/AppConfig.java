package com.alasdoo.controllers;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alasdoo.repositories.CompassSearcher;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


/**
 * Appliaction configuration
 *Prepares the google user service, and the persistenceManagerFactory for use.
 * @author Vinofil
 *
 */
@Configuration
public class AppConfig {
 
    @Bean
    public UserService userService() {
        return UserServiceFactory.getUserService();
    }
    
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");


    @Bean
    public PersistenceManagerFactory persistenceManagerFactory() {
        return pmfInstance;
    }

    private static final CompassSearcher compassSearcher = new CompassSearcher(pmfInstance);
    
    @Bean
    public CompassSearcher compassSearcher(){
    	return compassSearcher;
    }
}
