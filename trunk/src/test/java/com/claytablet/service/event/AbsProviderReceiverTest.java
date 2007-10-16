package com.claytablet.service.event;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.claytablet.model.enm.ContentType;
import com.claytablet.model.enm.FileType;
import com.claytablet.model.event.platform.ApprovedAssetTask;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.RejectedAssetTask;
import com.claytablet.model.event.platform.StartAssetTask;
import com.claytablet.model.event.producer.CancelAssetTask;
import com.claytablet.util.LanguageUtil;

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
public abstract class AbsProviderReceiverTest extends TestCase {

	protected final Log log = LogFactory.getLog(getClass());

	protected ProviderReceiver receiver;

	protected final String PLATFORM_ACCOUNT_ID = "ctt-platform";

	// TODO - replace this with your assigned account identifier
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
	 * Test for ProcessingError event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testProcessingError() throws Exception {

		log.debug("Create a mock ProcessingError event.");
		ProcessingError event = new ProcessingError();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PROVIDER_ACCOUNT_ID);
		event.setErrorMessage("mock-error-message");
		event.setErrorDetails("mock-error-details");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for StartAssetTask event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStartAssetTask() throws Exception {

		log.debug("Create a mock StartAssetTask event.");
		StartAssetTask event = new StartAssetTask();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PROVIDER_ACCOUNT_ID);
		event.setProjectId("mock-project-id");
		event.setAssetId("mock-asset-id");
		event.setAssetTaskId("mock-asset-task-id");
		event.setSourceLanguageCode(LanguageUtil.getCode("English"));
		event.setTargetLanguageCode(LanguageUtil.getCode("French"));
		event.setContentType(ContentType.Technical);
		event.setFileType(FileType.Text);
		event.setFileExt("txt");
		event.setName("mock-name");
		event.setTags("tag1, tag2");
		event.setDescription("mock-description");

		log.debug("Upload the asset task version file that is to be started.");
		receiver.uploadAssetTaskVersion(event.getTargetAccountId(), event
				.getAssetTaskId(), event.getFileExt(), "assetTaskVersion.txt");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CancelAssetTask event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCancelAssetTask() throws Exception {

		log.debug("Create a mock CancelAssetTask event.");
		CancelAssetTask event = new CancelAssetTask();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PROVIDER_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for RejectAssetTask event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRejectAssetTask() throws Exception {

		log.debug("Create a mock RejectedAssetTask event.");
		RejectedAssetTask event = new RejectedAssetTask();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PROVIDER_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");
		event.setReviewNote("mock-review-note");
		event.setWithContent(true);

		log.debug("Upload the asset task version file that has been rejected.");
		receiver.uploadAssetTaskVersion(event.getTargetAccountId(), event
				.getAssetTaskId(), event.getFileExt(), "assetTaskVersion.txt");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for ApproveAssetTask event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testApproveAssetTask() throws Exception {

		log.debug("Create a mock ApprovedAssetTask event.");
		ApprovedAssetTask event = new ApprovedAssetTask();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PROVIDER_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");
		event.setReviewNote("mock-review-note");
		event.setWithContent(true);

		log.debug("Upload the asset task version file that has been approved.");
		receiver.uploadAssetTaskVersion(event.getTargetAccountId(), event
				.getAssetTaskId(), event.getFileExt(), "assetTaskVersion.txt");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

}
