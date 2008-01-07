package com.claytablet.service.event.mock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.ConnectionContext;
import com.claytablet.model.event.provider.UpdateAssetTaskState;
import com.claytablet.service.event.ProviderSender;
import com.claytablet.service.event.ProviderStatePoller;
import com.claytablet.service.event.mock.stub.MockStub;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
 * This is the mock implementation for the provider state poller.
 * 
 * @see ProviderStatePoller
 * @see ProviderSender
 * @see UpdateAssetTask
 */
@Singleton
public class ProviderStatePollerMock implements ProviderStatePoller {

	private final Log log = LogFactory.getLog(getClass());

	private final ConnectionContext context;

	private final ProviderSender providerSender;

	private final MockStub mockStub;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param providerSender
	 * @param mockStub;
	 */
	@Inject
	public ProviderStatePollerMock(final ConnectionContext context,
			final ProviderSender providerSender, final MockStub mockStub) {
		this.context = context;
		this.providerSender = providerSender;
		this.mockStub = mockStub;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProviderStatePoller#poll()
	 */
	public void poll() throws Exception {

		log.debug("Polling TMS for asset task state changes.");

		UpdateAssetTaskState event = new UpdateAssetTaskState();
		event.setAssetTaskId("mock-id");
		event.setNativeState("mock-tms-state");
		event.setNotes("mock-notes");
		providerSender.sendEvent(event);

	}

}
