package com.alasdoo.service;

import org.compass.core.CompassException;
import org.compass.core.config.CompassConfigurable;
import org.compass.core.config.CompassSettings;
import org.compass.core.converter.ConversionException;
import org.compass.core.converter.basic.AbstractBasicConverter;
import org.compass.core.mapping.ResourcePropertyMapping;
import org.compass.core.marshall.MarshallingContext;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class KeyStringCompassConverter extends AbstractBasicConverter implements CompassConfigurable{

	@Override
	public void configure(CompassSettings arg0) throws CompassException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Object doFromString(String arg0, ResourcePropertyMapping arg1,
			MarshallingContext arg2) throws ConversionException {
		
		return KeyFactory.stringToKey(arg0);
	}

	@Override
	protected String doToString(Object o,
			ResourcePropertyMapping resourcePropertyMapping,
			MarshallingContext context) {
		// TODO Auto-generated method stub
		return KeyFactory.keyToString((Key) o);
	}


}
