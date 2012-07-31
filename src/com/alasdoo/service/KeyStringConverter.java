package com.alasdoo.service;

import java.beans.PropertyEditorSupport;

import org.datanucleus.util.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

final public class KeyStringConverter extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.isEmpty(text))
			setValue(KeyFactory.stringToKey(text));
		else
			setValue(null);
	}
	@Override
	public String getAsText() {
		if (getValue() == null)
			return null;
		return KeyFactory.keyToString((Key)getValue());

	}
}
