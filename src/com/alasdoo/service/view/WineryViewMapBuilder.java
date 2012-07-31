package com.alasdoo.service.view;

import com.alasdoo.entity.Winery;
import com.alasdoo.service.PictureService;

/**
 * Builds a winery view Map
 * Includes every detail of the winery + picture of the winery
 * @author turzait
 *
 */
public class WineryViewMapBuilder extends ViewMapBuilder {
	Winery winery;



	public WineryViewMapBuilder(Winery winery){
		this.winery = winery;

	}
	public WineryViewMapBuilder(Winery winery,PictureService pictureService){
		this.winery = winery;
		this.pictureService = pictureService;
	}

	private PictureService pictureService;

	@Override
	public void addDetailsView() {
		objectMap.put("wineryWebsite", winery.getWebsite());
		objectMap.put("wineryEmail",winery.getEmail());
		objectMap.put("wineryCity",winery.getCity());
		objectMap.put("wineryAbout",winery.getAbout());
		objectMap.put("wineryAddress",winery.getAddress());
		objectMap.put("wineryPhone",winery.getPhone());
		if (winery.getPictures() != null)
			objectMap.put("wineryPictures", pictureService.getPicturesListFromPictureKeys(winery.getPictures()));

		if (winery.getProfilePicture() != null)
			objectMap.put("wineryProfilePicture", (pictureService).getPictureUrl((Long)winery.getProfilePicture()));

	}

	@Override
	public void addMainView() {
		objectMap.put("wineryUrl", winery.getUrl_name());
		objectMap.put("wineryName", winery.getName());
	}

}
