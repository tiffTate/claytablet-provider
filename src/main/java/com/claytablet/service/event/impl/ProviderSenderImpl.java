package com.claytablet.service.event.impl;

import com.claytablet.factory.QueuePublisherServiceFactory;
import com.claytablet.factory.StorageClientServiceFactory;
import com.claytablet.model.event.provider.AcceptAssetTask;
import com.claytablet.model.event.provider.SubmitAssetTask;
import com.claytablet.model.event.provider.UpdateAssetTaskState;
import com.claytablet.queue.service.QueuePublisherService;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProviderSender;
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
 * This is the default implementation for the provider sender.
 * 
 * @see AbsEventClientImpl
 * @see QueuePublisherService
 * @see StorageClientService
 */
@Singleton
public class ProviderSenderImpl extends AbsEventClientImpl implements
		ProviderSender {

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param queuePublisherServiceFactory
	 * @param storageClientServiceFactory
	 */
	@Inject
	public ProviderSenderImpl(
			QueuePublisherServiceFactory queuePublisherServiceFactory,
			StorageClientServiceFactory storageClientServiceFactory) {
		this.queuePublisherServiceFactory = queuePublisherServiceFactory;
		this.storageClientServiceFactory = storageClientServiceFactory;
	}

	// -------------------------------------------------------------------------
	// Event methods
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProviderSender#sendEvent(com.claytablet.model.event.provider.AcceptAssetTask)
	 */
	public void sendEvent(AcceptAssetTask event) throws EventServiceException,
			QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		// send the event
		super.sendEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProviderSender#sendEvent(com.claytablet.model.event.provider.SubmitAssetTask,
	 *      java.lang.String)
	 */
	public void sendEvent(SubmitAssetTask event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		log.debug("Check to make sure a file path has been specified.");
		if (sourceFilePath == null || sourceFilePath.length() == 0) {
			throw new EventServiceException(
					"The source file path was invalid. A file must be specified for upload when submitting an asset task.");
		}

		log.debug("Determine the file extension using the source file path.");
		String fileExt = sourceFilePath.substring(sourceFilePath
				.lastIndexOf(".") + 1, sourceFilePath.length());

		log.debug("Upload the asset task version file.");
		uploadAssetTaskVersion(event.getSourceAccountId(), event
				.getAssetTaskId(), fileExt, sourceFilePath);

		// send the event
		super.sendEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProviderSender#sendEvent(com.claytablet.model.event.provider.UpdateAssetTaskState)
	 */
	public void sendEvent(UpdateAssetTaskState event)
			throws EventServiceException, QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		// send the event
		super.sendEvent(event);
	}

}
