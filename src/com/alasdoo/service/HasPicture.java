package com.alasdoo.service;

import java.util.List;

public interface HasPicture {

    public List<Long> getPictures();
	public Long getProfilePicture();
	public void setProfilePicture(Long profilePicture);
	public void addPicture(Long pictureId);
	public void setPictures(List<Long> pictures);
    public String getPictureName();
}
