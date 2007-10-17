package com.claytablet.service.event.mock;

import com.claytablet.factory.QueuePublisherServiceFactory;
import com.claytablet.factory.StorageClientServiceFactory;
import com.claytablet.model.event.platform.ApprovedAssetTask;
import com.claytablet.model.event.platform.CanceledAssetTask;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.RejectedAssetTask;
import com.claytablet.model.event.platform.StartAssetTask;
import com.claytablet.model.event.provider.SubmitAssetTask;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProviderReceiver;
import com.claytablet.service.event.ProviderSender;
import com.claytablet.service.event.impl.AbsEventClientImpl;
import com.claytablet.storage.service.StorageClientService;
import com.claytablet.storage.service.StorageServiceException;
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
 * This is the default implementation for the provider receiver.
 * 
 * <p>
 * @see StorageClientService
 */
@Singleton
public class ProviderReceiverMock extends AbsEventClientImpl implements
		ProviderReceiver {

	// we're going to automatically respond to messages
	private ProviderSender providerSender;

	private static final String PLATFORM_ACCOUNT_ID = "ctt-platform";

	// TODO - replace this with your assigned account identifier
	private static final String PROVIDER_ACCOUNT_ID = "ctt-provider-tms1";

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param queuePublisherServiceFactory
	 * @param storageClientServiceFactory
	 * @param providerSender
	 */
	@Inject
	public ProviderReceiverMock(
			QueuePublisherServiceFactory queuePublisherServiceFactory,
			StorageClientServiceFactory storageClientServiceFactory,
			ProviderSender providerSender) {
		this.queuePublisherServiceFactory = queuePublisherServiceFactory;
		this.storageClientServiceFactory = storageClientServiceFactory;
		this.providerSender = providerSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.ApprovedAssetTask)
	 */
	public void receiveEvent(ApprovedAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// check to see if new content was submitted with the event
		if (event.isWithContent()) {
			log.debug("A new asset task revision was sent with the approval.");

			log.debug("Download the latest asset task revision for: "
					+ event.getAssetTaskId());
			String downloadPath = super.downloadLatestAssetTaskVersion(event
					.getTargetAccountId(), event.getAssetTaskId(),
					"./files/received/");

			log.debug("Downloaded an asset task version file to: "
					+ downloadPath);
		}

		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.CanceledAssetTask)
	 */
	public void receiveEvent(CanceledAssetTask event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// do nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProvider#receiveEvent(com.claytablet.model.event.platform.ProcessingError)
	 */
	public void receiveEvent(ProcessingError event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// do nothing

		log.error(event.getErrorMessage());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.RejectedAssetTask)
	 */
	public void receiveEvent(RejectedAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// check to see if new content was submitted with the event
		if (event.isWithContent()) {
			log.debug("A new asset task revision was sent with the rejection.");

			log.debug("Download the latest asset task revision for: "
					+ event.getAssetTaskId());
			String downloadPath = super.downloadLatestAssetTaskVersion(event
					.getTargetAccountId(), event.getAssetTaskId(),
					"./files/received/");

			log.debug("Downloaded an asset task version file to: "
					+ downloadPath);

			log.debug("Submit the mock event.");
			SubmitAssetTask event2 = new SubmitAssetTask();
			event2.setSourceAccountId(PROVIDER_ACCOUNT_ID);
			event2.setTargetAccountId(PLATFORM_ACCOUNT_ID);
			event2.setAssetTaskId(event.getAssetTaskId());
			event2.setNativeState("Mock State");

			try {
				providerSender.sendEvent(event2, downloadPath);
			} catch (QueueServiceException e) {
				log.error(e);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProvider#receiveEvent(com.claytablet.model.event.platform.StartAssetTask)
	 */
	public void receiveEvent(StartAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		log.debug("Download the latest asset task revision for: "
				+ event.getAssetTaskId());
		String downloadPath = super.downloadLatestAssetTaskVersion(event
				.getTargetAccountId(), event.getAssetTaskId(),
				"./files/received/");

		log.debug("Downloaded an asset task version file to: " + downloadPath);

		log.debug("Submit the mock event.");
		SubmitAssetTask event2 = new SubmitAssetTask();
		event2.setSourceAccountId(PROVIDER_ACCOUNT_ID);
		event2.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event2.setAssetTaskId(event.getAssetTaskId());
		event2.setNativeState("Mock State");

		try {
			providerSender.sendEvent(event2, downloadPath);
		} catch (QueueServiceException e) {
			log.error(e);
		}

	}

}
