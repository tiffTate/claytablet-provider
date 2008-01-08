package com.claytablet.model;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;

public abstract class AbsProjectMapTest extends TestCase {

	protected final Log log = LogFactory.getLog(getClass());

	protected ProjectMap projectMap;

	// -------------------------------------------------------------------------
	// Initializations
	// -------------------------------------------------------------------------

	/**
	 * This is run before every unit test and is used to setup test variables.
	 */
	@Before
	public void setUp() {

		log.debug("SETUP: ");

		log.debug("Inject the event project map.");
		projectMap = Guice.createInjector().getInstance(ProjectMap.class);
	}

	/**
	 * This is run after every unit test and is used to undo changes as
	 * necessary.
	 */
	@After
	public void tearDown() {

		log.debug("TEARDOWN: ");
	}

	// -------------------------------------------------------------------------
	// Tests
	// -------------------------------------------------------------------------

	/**
	 * Test for save & load.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSaveAndLoad() throws Exception {

		log
				.debug("Clean existing project map and populate it with new values.");
		projectMap.clear();
		projectMap.put("mock-provider-project-id1", new ProjectMapping(
				"ctt-project-id", "en-us", "fr"));
		projectMap.put("mock-provider-project-id2", new ProjectMapping(
				"ctt-project-id", "en-us", "de"));

		log.debug("Save it.");
		projectMap.save();
		assertNotNull(projectMap);

		log.debug("Load it.");
		projectMap.load();

		log.debug("Size: " + projectMap.size());
		assertNotNull(projectMap);

	}
}
