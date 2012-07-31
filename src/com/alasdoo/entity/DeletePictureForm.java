package com.alasdoo.entity;

public class DeletePictureForm {
	String[] picUrl;
	String objectName;
	String defaultPicture; 

	public String[] getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String[] picUrl) {
		this.picUrl = picUrl;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getDefaultPicture() {
		return defaultPicture;
	}
 
	public void setDefaultPicture(String defaultPicture) {
		this.defaultPicture = defaultPicture;
	}




}
