package com.alasdoo.entity;

import javax.validation.Valid;

/**
 * Data binding class
 * @author warden
 *
 */
public class ReviewForm {

	@Valid
	private Review review;
	
	private String wineName;
	
	private String wineUrl;
	
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
	public String getWineName() {
		return wineName;
	}
	public void setWineName(String wineName) {
		this.wineName = wineName;
	}
	public String getWineUrl() {
		return wineUrl;
	}
	public void setWineUrl(String wineUrl) {
		this.wineUrl = wineUrl;
	}
	
	
	
	
}
