package com.claytablet.service.event;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.claytablet.model.event.provider.AcceptAssetTask;
import com.claytablet.model.event.provider.SubmitAssetTask;
import com.claytablet.model.event.provider.UpdateAssetTaskState;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at <a
 * href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a>
 * 
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Tests for the provider receiver.
 * 
 * <p>
 * @see ProviderReceiver
 */
public abstract class AbsProviderSenderTest extends TestCase {

	protected final Log log = LogFactory.getLog(getClass());

	protected ProviderSender sender;

	protected final String PLATFORM_ACCOUNT_ID = "ctt-platform";

	protected final String PROVIDER_ACCOUNT_ID = "ctt-provider-tms1";

	// -------------------------------------------------------------------------
	// Initializations
	// -------------------------------------------------------------------------

	/**
	 * This is run before every unit test and is used to setup test variables.
	 */
	@Before
	public void setUp() {

		log.debug("SETUP: ");

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
	 * Test for AcceptAssetTask event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAcceptAssetTask() throws Exception {

		log.debug("Create a mock AcceptAssetTask event.");
		AcceptAssetTask event = new AcceptAssetTask();
		event.setSourceAccountId(PROVIDER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");
		event.setAssetTaskNativeId("mock-native-id");

		log.debug("Send the event.");
		sender.sendEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for SubmitAssetTask event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubmitAssetTask() throws Exception {

		log.debug("Create a mock SubmitAssetTask event.");
		SubmitAssetTask event = new SubmitAssetTask();
		event.setSourceAccountId(PROVIDER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");
		event.setNativeState("mock-native-state");
		event.setFileExt("txt");

		// specify the file to be sent, using the default source directory
		String sourceFilePath = "assetTaskVersion.txt";

		sender.sendEvent(event, sourceFilePath);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for UpdateAssetTaskState event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateAssetTaskState() throws Exception {

		log.debug("Create a mock UpdateAssetTaskState event.");
		UpdateAssetTaskState event = new UpdateAssetTaskState();
		event.setSourceAccountId(PROVIDER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");
		event.setNativeState("mock-native-state");

		sender.sendEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

}
