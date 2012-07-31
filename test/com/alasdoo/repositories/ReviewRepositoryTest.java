package com.alasdoo.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alasdoo.service.LocalDatastoreTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReviewRepositoryTest  extends LocalDatastoreTest{

	@Autowired
	private ReviewRepository reviewRepository;
	
	
	
}
