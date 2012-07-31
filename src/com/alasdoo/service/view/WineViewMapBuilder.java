package com.alasdoo.service.view;

import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineForm;
import com.alasdoo.entity.Winery;
import com.alasdoo.repositories.WineRepository;
import com.alasdoo.service.PictureService;
import com.alasdoo.service.WineService;


public class WineViewMapBuilder extends ViewMapBuilder {

	private PictureService pictureService;
	
	private WineRepository wineRepository;
	
	private WineService wineService;
	
	private Wine wine;
	
	
	public WineViewMapBuilder(Wine wine,WineRepository wineRepository,WineService wineService,PictureService pictureService){
		this.wine = wine;
		this.wineRepository = wineRepository;
		this.pictureService = pictureService;
		this.wineService = wineService;
	}
	
	@Override
	public void addDetailsView() {
		 WineForm wineForm = wineService.convertWineToWineForm(wine);
		 		 
		objectMap.put("wineVintage", wine.getVintage());
		objectMap.put("wineType", wineForm.getType());
		objectMap.put("wineAlcohol", wine.getAlcohol());
		objectMap.put("wineBottleSize", wine.getProductionDetails().getBottleSize());
		objectMap.put("wineAveragePrice", wine.getAveragePrice());
		objectMap.put("wineCharacters", wineForm.getCharacterList());
		objectMap.put("wineComposition", wineForm.getCompositionList());
		objectMap.put("wineAged", wineForm.getAgedList());
		objectMap.put("wineAmountProduced", wine.getProductionDetails().getAmountProduced());
		objectMap.put("wineClosure", wine.getProductionDetails().getClosure());
		objectMap.put("winePictures", pictureService.getPicturesListFromPictureKeys(wine.getPictures()));
	}

	@Override
	public void addMainView() {
			
			Winery winery = wineRepository.getWineryByKey(wine.getWinery());
			ViewMapDirector vd = new ViewMapDirector();		
			
			vd.setMapBuilder(new WineryViewMapBuilder(winery));
			vd.constructShortViewMap();
			objectMap.putAll(vd.getMapObject());
			
			objectMap.put("wineUrl",wine.getUrl_name());
			objectMap.put("wineName",wine.getName());
			if (wineRepository.getWineStyle(wine) !=null)
			objectMap.put("wineStyle", wineRepository.getWineStyle(wine));
			objectMap.put("wineAverageRating",wine.getAverageRating());
			objectMap.put("wineNumberOfReviews",wine.getNumberOfReviews());
			objectMap.put("wineNotes",wine.getNotes());
			if (wine.getProfilePicture() != null)
				objectMap.put("wineProfilePicture", pictureService.getPictureUrl(wine.getProfilePicture()));
			
	}
	

}
