package com.claytablet.service.event;

import com.claytablet.model.event.provider.AcceptAssetTask;
import com.claytablet.model.event.provider.SubmitAssetTask;
import com.claytablet.model.event.provider.UpdateAssetTaskState;
import com.claytablet.queue.service.QueuePublisherService;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.impl.ProviderSenderImpl;
import com.claytablet.storage.service.StorageClientService;
import com.claytablet.storage.service.StorageServiceException;
import com.google.inject.ImplementedBy;

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
 * The provider sender is responsible for sending provider specific events and
 * transmitting asset task files to the Clay Tablet platform.
 * 
 * @see StorageClientService
 * @see QueuePublisherService
 */
@ImplementedBy(ProviderSenderImpl.class)
public interface ProviderSender {

	// -------------------------------------------------------------------------
	// Event methods
	// -------------------------------------------------------------------------

	/**
	 * Sends an accept asset task event. This lets the Clay Tablet platform and
	 * the producer know that the asset task has arrived propoerly and work may
	 * commence.
	 * 
	 * @param event
	 *            Required parameter that specifies the accept asset task event
	 *            to send.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(AcceptAssetTask event) throws EventServiceException,
			QueueServiceException;

	/**
	 * Sends a submit asset task event, and uploads it's associated revision
	 * file.
	 * 
	 * @param event
	 *            Required parameter that specifies the submit asset task event
	 *            to send.
	 * @param sourceFilePath
	 *            Required parameter that specifies the path to the asset task
	 *            revision file to be uploaded.
	 * @throws EventServiceException
	 * @throws StorageServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(SubmitAssetTask event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException;

	/**
	 * Sends an update asset task state event. This saves the new native state
	 * to the platform for producers to see.
	 * 
	 * @param event
	 *            Required parameter that specifies the update asset task state
	 *            event to send.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(UpdateAssetTaskState event)
			throws EventServiceException, QueueServiceException;

}
