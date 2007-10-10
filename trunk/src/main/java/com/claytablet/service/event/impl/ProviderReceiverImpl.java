package com.claytablet.service.event.impl;

import com.claytablet.factory.QueuePublisherServiceFactory;
import com.claytablet.factory.StorageClientServiceFactory;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.StartAssetTask;
import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.model.event.producer.CancelAssetTask;
import com.claytablet.model.event.producer.RejectAssetTask;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProviderReceiver;
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
 * the License at
 * 
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
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
public class ProviderReceiverImpl extends AbsEventClientImpl implements
		ProviderReceiver {

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param queuePublisherServiceFactory
	 * @param storageClientServiceFactory
	 */
	@Inject
	public ProviderReceiverImpl(
			QueuePublisherServiceFactory queuePublisherServiceFactory,
			StorageClientServiceFactory storageClientServiceFactory) {
		this.queuePublisherServiceFactory = queuePublisherServiceFactory;
		this.storageClientServiceFactory = storageClientServiceFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.ApproveAssetTask)
	 */
	public void receiveEvent(ApproveAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// check to see if new content was submitted with the event
		if (event.isWithContent()) {
			log.debug("A new asset task revision was sent with the approval.");

			log.debug("Download the latest asset task revision for: "
					+ event.getAssetTaskId());
			super.downloadLatestAssetTaskVersion(event.getTargetAccountId(),
					event.getAssetTaskId());
		}

		// TODO - provider integration code goes here.

		// If an exception is thrown the event will remain on the queue.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.CancelAssetTask)
	 */
	public void receiveEvent(CancelAssetTask event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// TODO - provider integration code goes here.

		// If an exception is thrown the event will remain on the queue.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProvider#receiveEvent(com.claytablet.model.event.platform.ProcessingError)
	 */
	public void receiveEvent(ProcessingError event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// TODO - provider integration code goes here.
		// An error occured. Examine and take appropriate action.

		log.error(event.getErrorMessage());

		// The event.getErrorDetails() will contain the original serialized
		// event that caused the error. It can be deserialized into it's event
		// object and dealt with.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.RejectAssetTask)
	 */
	public void receiveEvent(RejectAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// check to see if new content was submitted with the event
		if (event.isWithContent()) {
			log.debug("A new asset task revision was sent with the rejection.");

			log.debug("Download the latest asset task revision for: "
					+ event.getAssetTaskId());
			super.downloadLatestAssetTaskVersion(event.getTargetAccountId(),
					event.getAssetTaskId());
		}

		// TODO - provider integration code goes here.

		// If an exception is thrown the event will remain on the queue.
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
		super.downloadLatestAssetTaskVersion(event.getTargetAccountId(), event
				.getAssetTaskId());

		// TODO - provider integration code goes here.
		// I.e. send the asset to the TMS and mark it as pending.

		// I.e. call an external system method called new document

		// If an exception is thrown the event will remain on the queue.

	}

}
