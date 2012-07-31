package com.alasdoo.repositories;

import java.util.logging.Logger;

import javax.jdo.PersistenceManagerFactory;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.gps.CompassGps;
import org.compass.gps.device.jdo.Jdo2GpsDevice;
import org.compass.gps.impl.SingleCompassGps;

import com.alasdoo.service.KeyStringCompassConverter;
import com.google.appengine.api.NamespaceManager;
import com.google.apphosting.api.ApiProxy;

public class CompassSearcher {
	
	private static final Logger log = Logger.getLogger(CompassSearcher.class.getName());
	
	private Compass compass;
	
	private CompassGps compassGps;

	public final Compass getCompass(){
		return compass;
	}
	public CompassSearcher(PersistenceManagerFactory pmfInstance){
		//System.out.println(NamespaceManager.get());
    	//System.out.println(ApiProxy.getCurrentEnvironment().getAppId());
    	//System.out.println(ApiProxy.getCurrentEnvironment().getAuthDomain());
//		try{
			

//		compass  = new CompassConfiguration().setConnection("gae://index")		
//		.setSetting(CompassEnvironment.ExecutorManager.EXECUTOR_MANAGER_TYPE, "disabled")
//		.addScan("com.alasdoo.entity")
//		.registerConverter("keyToString", new KeyStringCompassConverter())
//		.setSetting("compass.engine.spellcheck.enable", "true")
//		.buildCompass();
//		compassGps = new SingleCompassGps(compass);
//		compassGps.addGpsDevice(new Jdo2GpsDevice("appenine", pmfInstance));
//		compassGps.start();
//		long time1 = System.currentTimeMillis();
//		log.info("Indexing database.");
//		compassGps.index();
//		long time2 = System.currentTimeMillis();
//		log.info("First db indexing finished:" + (time2 - time1) + "ms");
//		}catch(NullPointerException ex){
//			ex.printStackTrace();
//		}
	}

}
