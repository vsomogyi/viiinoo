package com.alasdoo.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alasdoo.entity.Picture;
import com.alasdoo.service.PictureService;

@RunWith(SpringJUnit4ClassRunner.class)
public class PictureServiceTest  extends LocalDatastoreTest{

	@Autowired
	private PictureService pictureService;

//
//	@Test
//	public void testSave(){
//		 
//		Picture picture = new Picture();
//		pictureService.save(picture);
//	}
	
	@Test
	public void testGetAll(){
		Picture picture = new Picture();
		pictureService.save(picture);
		List<Picture> result = pictureService.getAll();
		assertEquals(1,result.size());
	}
	
	@Test
	public void testGet(){
		Picture picture = new Picture();
		picture.setName("Pic");
		pictureService.save(picture);
		List<Picture> result = pictureService.getAll();
		Picture testPic = pictureService.get(result.get(0).getId());
		assertEquals(picture.getName(),testPic.getName());
	}
}
