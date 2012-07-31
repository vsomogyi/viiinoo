package com.alasdoo.testCases;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.alasdoo.repositories.ReviewRepositoryTest;
import com.alasdoo.repositories.WineEnthusiastRepositoryTest;
import com.alasdoo.repositories.WineRepositoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { WineEnthusiastRepositoryTest.class,WineRepositoryTest.class,ReviewRepositoryTest.class  })

public class RepositoryTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(RepositoryTests.class.getName());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}

}
